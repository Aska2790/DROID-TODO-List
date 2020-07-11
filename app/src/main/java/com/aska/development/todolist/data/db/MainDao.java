package com.aska.development.todolist.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class MainDao {

    //region Delete

    @Query("DELETE FROM TaskEntity")
    public abstract void deleteAll();

    //endregion

    //region Select

    @Query("SELECT * FROM TaskEntity WHERE task_id=:remoteId")
    public abstract TaskEntity getTask(String remoteId);

    @Query("SELECT * FROM TaskEntity WHERE id=:id")
    public abstract LiveData<TaskEntity> getObservedTask(long id);

    @Query("SELECT * FROM TaskEntity")
    public abstract LiveData<List<TaskEntity>> getObservedTasks();

    //endregion

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(TaskEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<TaskEntity> entities);
    //endregion
}
