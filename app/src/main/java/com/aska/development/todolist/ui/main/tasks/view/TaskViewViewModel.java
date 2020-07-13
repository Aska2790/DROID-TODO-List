package com.aska.development.todolist.ui.main.tasks.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.auxiliary.Result;
import com.aska.development.todolist.data.auxiliary.SingleLiveEvent;
import com.aska.development.todolist.data.reposotories.main.MainRepository;
import com.aska.development.todolist.domain.interactors.DeleteTaskUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.ui.auxiliary.UiItemMapper;
import com.aska.development.todolist.ui.main.tasks.TaskItemViewModel;

import javax.inject.Inject;

public class TaskViewViewModel extends ViewModel {

    //region Fields
    private MainRepository mRepository;
    private SingleLiveEvent<Result<Object>> mState;
    //endregion

    //region Properties

    public LiveData<Result<Object>> getState() {
        return mState;
    }

    public LiveData<TaskItemViewModel> getTaskItem(String id) {
        return Transformations.map(mRepository.getObservedTask(id), UiItemMapper::map);
    }

    //endregion

    //region Constructors

    @Inject
    public TaskViewViewModel(MainRepository repository) {
        mRepository = repository;
        mState = new SingleLiveEvent<>();
    }

    //endregion

    //region Methods
    public void deleteTask(String taskId) {

        mState.postValue(Result.loading(taskId));

        new DeleteTaskUseCase()
                .setTaskId(taskId)
                .setExecutor(mRepository)
                .setListener(new AsyncActionListener() {
                    @Override
                    public void onSuccess(@Nullable Object object) {
                        mState.postValue(Result.success(object));
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        mState.postValue(Result.error(throwable.getMessage(), null));
                    }
                })
                .execute();
    }

    //endregion
}

