package com.aska.development.todolist.data.reposotories.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.aska.development.todolist.data.auxiliary.DbItemMapper;
import com.aska.development.todolist.data.db.MainDao;
import com.aska.development.todolist.data.db.TaskEntity;
import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.domain.interactors.AddTaskUseCase;
import com.aska.development.todolist.domain.interactors.DeleteTaskUseCase;
import com.aska.development.todolist.domain.interactors.UpdateTaskUseCase;
import com.aska.development.todolist.domain.model.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainRepositoryImpl implements MainRepository, AuthService.ProfileStateListener , ChildEventListener{
    //region Fields

    private static final String TASKS_REF = "tasks";
    private LiveData<List<Task>> mObservedTaskList;
    private AuthService mAuthService;
    private MainDao mMainDao;
    private DatabaseReference mDatabaseReference;

    //endregion

    //region Properties

    @Override
    public LiveData<List<Task>> getObservedTaskList() {
        return mObservedTaskList;
    }

    @Override
    public LiveData<Task> getObservedTask(String id) {
        return Transformations.map(mMainDao.getObservedTask(id), DbItemMapper::map);
    }

    //endregion

    //region Constructors

    public MainRepositoryImpl(AuthService authService, MainDao mainDao) {
        mAuthService = authService;
        mMainDao = mainDao;
        LiveData<List<TaskEntity>> observedTasks = mMainDao.getObservedTasks();
        mObservedTaskList = Transformations.map(observedTasks, entities -> {
            if (entities != null) {
                List<Task> tasks = new ArrayList<>();
                for (TaskEntity entity : entities) {
                    tasks.add(DbItemMapper.map(entity));
                }
                return tasks;
            }
            return null;
        });

        mAuthService.addProfileStateListener(this);

    }

    //endregion

    //region Methods

    @Override
    public void addTask(Task task, AddTaskUseCase.Callback callback) {

        if (mDatabaseReference == null) {
            throw new IllegalStateException();
        }

        if(callback == null){
            throw new IllegalArgumentException();
        }

        mDatabaseReference.push().setValue(task)
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task1.getException());
                    }
                });

    }

    @Override
    public void deleteTask(String taskId, DeleteTaskUseCase.Callback callback) {

        if (mDatabaseReference == null) {
            throw new IllegalStateException();
        }

        if(callback == null){
            throw new IllegalArgumentException();
        }

        mDatabaseReference.child(taskId)
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });

    }

    @Override
    public void updateTask(Task task, UpdateTaskUseCase.Callback callback) {

        if(task == null){
            throw new NullPointerException();
        }

        if (mDatabaseReference == null) {
            throw new IllegalStateException();
        }

        if(callback == null){
            throw new IllegalArgumentException();
        }

        String taskKey = task.getId();

        // Ид задача является рут веткой
        task.setId(null);

        mDatabaseReference.child(taskKey)
                .setValue(task)
                .addOnCompleteListener(taskResult -> {
                    if (taskResult.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(taskResult.getException());
                    }
                });

    }

    @Override
    public void onSigned() {
        mDatabaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child(TASKS_REF)
                .child(mAuthService.getProfile().getUID());
        Executors.newSingleThreadExecutor().submit(()->{
            mMainDao.deleteAll();
            mDatabaseReference.addChildEventListener(MainRepositoryImpl.this);
        });
    }

    @Override
    public void onSignOut() {
        if(mDatabaseReference != null) {
            mDatabaseReference.removeEventListener(this);
        }
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Executors.newSingleThreadExecutor().submit(()->{
            TaskEntity entity = DbItemMapper.map(snapshot.getValue());
            entity.setRemoteId(snapshot.getKey());
            mMainDao.insert(entity);
        });
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Executors.newSingleThreadExecutor().submit(()->{
            TaskEntity oldTaskEntity = mMainDao.getTask(snapshot.getKey());
            TaskEntity updatedTaskEntity = DbItemMapper.map(snapshot.getValue());
            updatedTaskEntity.setLocalId(oldTaskEntity.getLocalId());
            updatedTaskEntity.setRemoteId(snapshot.getKey());
            mMainDao.update(updatedTaskEntity);
        });
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
        Executors.newSingleThreadExecutor().submit(()->{
            mMainDao.delete(snapshot.getKey());
        });

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
    //endregion

}
