package com.aska.development.todolist.domain.model;

public class Task {
    //region Fields

    private String mId;
    private String mTitle;
    private String mDescription;

    //endregion

    //region Properties

    public String getId() {
        return mId;
    }

    public Task setId(String id) {
        mId = id;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public Task setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public Task setDescription(String description) {
        mDescription = description;
        return this;
    }

    //endregion

}
