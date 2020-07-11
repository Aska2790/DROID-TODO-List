package com.aska.development.todolist.ui.main.tasks.list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.reposotories.main.MainRepository;
import com.aska.development.todolist.domain.interactors.AddTaskUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.domain.model.Task;
import com.aska.development.todolist.ui.auxiliary.UiItemMapper;
import com.aska.development.todolist.ui.main.tasks.TaskItemViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

public class TaskListViewModel extends ViewModel {
    //region Fields
    private MainRepository mRepository;
    private LiveData<List<TaskItemViewModel>> mTaskItemViewModels;
    //endregion

    //region Properties
    public LiveData<List<TaskItemViewModel>> getTaskItemViewModels() {
        return mTaskItemViewModels;
    }
    //endregion

    //region Constructors

    @Inject
    public TaskListViewModel(MainRepository repository) {
        mRepository = repository;
        mTaskItemViewModels = Transformations.map(mRepository.getObservedTaskList(), taskList -> {
            List<TaskItemViewModel> itemViewModels = new ArrayList<>();
            if (taskList != null) {
                for (Task task : taskList) {
                    itemViewModels.add(UiItemMapper.map(task));
                }
            }
            return itemViewModels;
        });
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

    //region Inner 
    //endregion
}
