package com.aska.development.todolist.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDB extends RoomDatabase {
    public abstract MainDao getTaskDao();
}
