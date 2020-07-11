package com.aska.development.todolist.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskEntity {
    //region Fields

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mLocalId;

    @ColumnInfo(name = "task_id")
    private String mRemoteId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;
    //endregion

    //region Properties

    public long getLocalId() {
        return mLocalId;
    }

    public void setLocalId(long localId) {
        mLocalId = localId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getRemoteId() {
        return mRemoteId;
    }

    public void setRemoteId(String remoteId) {
        mRemoteId = remoteId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    //endregion
}
