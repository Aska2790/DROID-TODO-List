package com.aska.development.todolist.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MainDao {

    //region Delete

    @Query("DELETE FROM TaskEntity")
    void deleteAll();

    @Query("DELETE FROM TaskEntity WHERE task_id=:remoteId")
    void delete(String remoteId);

    //endregion

    //region Select

    @Query("SELECT * FROM TaskEntity WHERE task_id=:remoteId")
    TaskEntity getTask(String remoteId);

    @Query("SELECT * FROM TaskEntity WHERE task_id=:remoteId")
    LiveData<TaskEntity> getObservedTask(String remoteId);

    @Query("SELECT * FROM TaskEntity")
    LiveData<List<TaskEntity>> getObservedTasks();

    //endregion

    //region Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TaskEntity entity);

    //endregion

    //region Update

    @Update
    void update(TaskEntity entity);

    //endregion
}
