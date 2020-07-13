package com.aska.development.todolist.data.reposotories.main;

import androidx.lifecycle.LiveData;

import com.aska.development.todolist.domain.interactors.AddTaskUseCase;
import com.aska.development.todolist.domain.interactors.DeleteTaskUseCase;
import com.aska.development.todolist.domain.interactors.UpdateTaskUseCase;
import com.aska.development.todolist.domain.model.Task;

import java.util.List;

public interface MainRepository extends AddTaskUseCase.Executor, DeleteTaskUseCase.Executor, UpdateTaskUseCase.Executor {

    LiveData<List<Task>> getObservedTaskList();

    LiveData<Task> getObservedTask(String id);
}
