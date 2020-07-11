package com.aska.development.todolist.ui.auxiliary;

import com.aska.development.todolist.domain.model.Task;
import com.aska.development.todolist.ui.main.tasks.TaskItemViewModel;

public class UiItemMapper {
    public static TaskItemViewModel map(Task task) {

        return new TaskItemViewModel()
                .setId(task.getId())
                .setTitle(task.getTitle())
                .setDescription(task.getDescription());
    }
}
