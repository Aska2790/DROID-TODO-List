package com.aska.development.todolist.ui.main.tasks;

public class TaskItemViewModel {

    //region Fields

    private String mId;
    private String mTitle;
    private String mDescription;

    //endregion

    //region Properties

    public String getId() {
        return mId;
    }

    public TaskItemViewModel setId(String id) {
        mId = id;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public TaskItemViewModel setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public TaskItemViewModel setDescription(String description) {
        mDescription = description;
        return this;
    }

    //endregion
}
