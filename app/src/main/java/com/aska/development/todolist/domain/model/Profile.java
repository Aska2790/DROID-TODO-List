package com.aska.development.todolist.domain.model;

public class Profile {
    //region Fields
    private String mUID;
    private String mFirstName;
    private String mSecondName;
    //endregion

    //region Properties


    public String getUID() {
        return mUID;
    }

    public Profile setUID(String UID) {
        mUID = UID;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public Profile setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getSecondName() {
        return mSecondName;
    }

    public Profile setSecondName(String secondName) {
        mSecondName = secondName;
        return this;
    }

    //endregion

}
