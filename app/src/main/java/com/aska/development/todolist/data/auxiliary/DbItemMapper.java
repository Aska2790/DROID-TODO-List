package com.aska.development.todolist.data.auxiliary;

import com.aska.development.todolist.data.db.TaskEntity;
import com.aska.development.todolist.domain.model.Task;

import java.util.HashMap;

public class DbItemMapper {

    public static TaskEntity map(Object remoteTaskSnapshot){
        Object remoteId = ((HashMap) remoteTaskSnapshot).get("id");
        Object title = ((HashMap) remoteTaskSnapshot).get("title");
        Object description = ((HashMap) remoteTaskSnapshot).get("description");

        TaskEntity entity = new TaskEntity();
        entity.setRemoteId((String)remoteId);
        entity.setTitle((String) title);
        entity.setDescription((String) description);

        return entity;
    }

    public static Task map(TaskEntity entity){

        Task model = new Task()
                .setId(entity.getRemoteId())
                .setTitle(entity.getTitle())
                .setDescription(entity.getTitle());

        return model;
    }
}
