package com.aska.development.todolist.data.reposotories.main;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.aska.development.todolist.data.auxiliary.DbItemMapper;
import com.aska.development.todolist.data.db.MainDao;
import com.aska.development.todolist.data.db.TaskEntity;
import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.domain.interactors.AddTaskUseCase;
import com.aska.development.todolist.domain.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

public class MainRepositoryImpl implements MainRepository, ValueEventListener {
    //region Fields

    private static final String TASKS_REF = "tasks";
    private LiveData<List<Task>> mObservedTaskList;
    private AuthService mAuthService;
    private MainDao mMainDao;
    private final DatabaseReference mDatabaseReference;

    //endregion

    //region Properties

    @Override
    public LiveData<List<Task>> getObservedTaskList() {
        return mObservedTaskList;
    }

    //endregion

    //region Constructors

    public MainRepositoryImpl(AuthService authService, MainDao mainDao) {
        mAuthService = authService;
        mMainDao = mainDao;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        if (mAuthService.getProfile() != null) {
            mDatabaseReference
                    .child(TASKS_REF)
                    .child(mAuthService.getProfile().getUID())
                    .addValueEventListener(this);

        }

        LiveData<List<TaskEntity>> observedTasks = mMainDao.getObservedTasks();
        mObservedTaskList = Transformations.map(observedTasks, (Function<List<TaskEntity>, List<Task>>) entities -> {
            if (entities == null) {
                return null;
            }

            List<Task> tasks = new ArrayList<>();
            for(TaskEntity entity: entities){
                tasks.add(DbItemMapper.map(entity));
            }

            return tasks;
        });

    }

    //endregion

    //region Methods

    @Override
    public void addTask(Task task, AddTaskUseCase.Callback callback) {
        callback.getClass();
        mDatabaseReference.child(TASKS_REF)
                .child(mAuthService.getProfile().getUID())
                .push()
                .setValue(task)
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                })
                .addOnCompleteListener(task1 -> {
                    callback.onSuccess();
                });

    }

    @Override
    public void deleteTask(String taskId) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                mMainDao.deleteAll();
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    Object value = iterator.next().getValue();
                    mMainDao.insert(DbItemMapper.map(value));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    //endregion

}
