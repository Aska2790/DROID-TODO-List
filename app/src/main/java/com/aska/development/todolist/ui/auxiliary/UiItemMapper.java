package com.aska.development.todolist.ui.auxiliary;

import com.aska.development.todolist.domain.model.Task;
import com.aska.development.todolist.ui.main.tasks.TaskItemViewModel;

public class UiItemMapper {
    public static TaskItemViewModel map(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskItemViewModel()
                .setId(task.getId())
                .setTitle(task.getTitle())
                .setDescription(task.getDescription());
    }

    public static Task map(TaskItemViewModel taskItemViewModel) {
        if (taskItemViewModel == null) {
            return null;
        }
        return new Task()
                .setId(taskItemViewModel.getId())
                .setTitle(taskItemViewModel.getTitle())
                .setDescription(taskItemViewModel.getDescription());
    }
}
