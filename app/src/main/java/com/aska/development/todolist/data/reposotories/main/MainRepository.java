package com.aska.development.todolist.data.reposotories.main;

import androidx.lifecycle.LiveData;

import com.aska.development.todolist.domain.interactors.AddTaskUseCase;
import com.aska.development.todolist.domain.model.Task;

import java.util.List;

public interface MainRepository extends AddTaskUseCase.Executor {

    LiveData<List<Task>> getObservedTaskList();

    void deleteTask(String taskId);

    void updateTask(Task task);
}
