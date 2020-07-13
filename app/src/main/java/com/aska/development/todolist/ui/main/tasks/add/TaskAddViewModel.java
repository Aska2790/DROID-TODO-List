package com.aska.development.todolist.ui.main.tasks.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.auxiliary.Result;
import com.aska.development.todolist.data.auxiliary.SingleLiveEvent;
import com.aska.development.todolist.data.reposotories.main.MainRepository;
import com.aska.development.todolist.domain.interactors.AddTaskUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.domain.model.Task;
import com.aska.development.todolist.ui.auxiliary.Validator;

import javax.inject.Inject;

public class TaskAddViewModel extends ViewModel {

    //region Fields

    private SingleLiveEvent<Result<Object>> mState;
    private MainRepository mRepository;
    private MutableLiveData<String> mTitle;
    private MutableLiveData<String> mDescription;
    private MutableLiveData<Boolean> isInputValid;

    //endregion

    //region Properties


    public LiveData<Result<Object>> getState() {
        return mState;
    }

    public MutableLiveData<String> getTitle() {
        return mTitle;
    }

    public MutableLiveData<String> getDescription() {
        return mDescription;
    }

    //endregion

    //region Constructors

    @Inject
    public TaskAddViewModel(MainRepository repository) {
        mRepository = repository;
        mTitle = new MutableLiveData<>();
        mDescription = new MutableLiveData<>();
        mState = new SingleLiveEvent<>();
        isInputValid = new MutableLiveData<>();
    }

    //endregion

    //region Methods

    public void addTask() {

        Task task = new Task()
                .setTitle(mTitle.getValue())
                .setDescription(mDescription.getValue());

        mState.postValue(Result.loading(task));

        new AddTaskUseCase()
                .setExecutor(mRepository)
                .setTask(task)
                .setListener(new AsyncActionListener() {
                    @Override
                    public void onSuccess(@Nullable Object object) {
                        mState.postValue(Result.success(object));
                        mDescription.postValue(null);
                        mTitle.postValue(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        mState.postValue(Result.error(throwable.getMessage(), null));
                    }
                })
                .execute();
    }

    public boolean isValid(){
        return Validator.isPlainTextValid(mDescription.getValue()) && Validator.isPlainTextValid(mTitle.getValue());
    }


    //endregion

}
