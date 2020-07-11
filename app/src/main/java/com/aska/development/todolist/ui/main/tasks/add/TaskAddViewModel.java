package com.aska.development.todolist.ui.main.tasks.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.reposotories.main.MainRepository;
import com.aska.development.todolist.domain.interactors.AddTaskUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.domain.model.Task;

import java.util.Random;

import javax.inject.Inject;

public class TaskAddViewModel extends ViewModel {

    //region Fields

    private MainRepository mRepository;

    //endregion

    //region Properties

    //endregion

    //region Constructors

    @Inject
    public TaskAddViewModel(MainRepository repository) {
        mRepository = repository;
    }

    public void addTask() {
        byte[] next = new byte[8];
        new Random().nextBytes(next);
        StringBuilder builder = new StringBuilder();
        for(byte _byte: next){
            builder.append((char) _byte);
        }

        Task task = new Task()
                .setTitle(builder.toString());


        new AddTaskUseCase()
                .setExecutor(mRepository)
                .setTask(task)
                .setListener(new AsyncActionListener() {
                    @Override
                    public void onSuccess(@Nullable Object object) {

                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }
                })
                .execute();
    }

    //endregion

    //region Methods
    //endregion

}
