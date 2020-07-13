package com.aska.development.todolist.ui.main.tasks.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.auxiliary.Result;
import com.aska.development.todolist.data.auxiliary.SingleLiveEvent;
import com.aska.development.todolist.data.reposotories.main.MainRepository;
import com.aska.development.todolist.domain.interactors.UpdateTaskUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.domain.model.Task;
import com.aska.development.todolist.ui.auxiliary.UiItemMapper;
import com.aska.development.todolist.ui.auxiliary.Validator;
import com.aska.development.todolist.ui.main.tasks.TaskItemViewModel;

import javax.inject.Inject;

public class TaskEditViewModel extends ViewModel {
    //region Fields

    private SingleLiveEvent<Result<Object>> mState;
    private MainRepository mRepository;
    private LiveData<TaskItemViewModel> mTaskItem;
    private TaskItemViewModel mItemViewModel;

    //endregion

    //region Properties

    public LiveData<Result<Object>> getState() {
        return mState;
    }


    public LiveData<TaskItemViewModel> getTaskItem(String taskId) {
        LiveData<Task> observedTask = mRepository.getObservedTask(taskId);
        mTaskItem = Transformations.map(observedTask, task -> {

            // Дублирование для сравнения
            mItemViewModel = UiItemMapper.map(task);

            return UiItemMapper.map(task);
        });

        return mTaskItem;
    }


    //endregion

    //region Constructors

    @Inject
    public TaskEditViewModel(MainRepository repository) {
        mRepository = repository;
        mState = new SingleLiveEvent<>();
    }

    //endregion

    //region Methods

    public void updateTask() {

        mState.postValue(Result.loading(null));

        new UpdateTaskUseCase()
                .setExecutor(mRepository)
                .setTask(UiItemMapper.map(mTaskItem.getValue()))
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

    public boolean isValid() {
        if (mTaskItem == null) {
            return false;
        }

        TaskItemViewModel value = mTaskItem.getValue();
        if (value == null || mItemViewModel == null) {
            return false;
        }

        if (!Validator.isPlainTextValid(value.getDescription())) {
            return false;
        }

        if (!Validator.isPlainTextValid(value.getTitle())) {
            return false;
        }

        if (value.getTitle().equals(mItemViewModel.getTitle())
                && value.getDescription().equals(mItemViewModel.getDescription())) {
            return false;
        }

        return true;
    }

    //endregion

}
