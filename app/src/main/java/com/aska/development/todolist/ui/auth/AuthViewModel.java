package com.aska.development.todolist.ui.auth;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    //region Fields

    private boolean isBackDoublePressed;

    //endregion

    //region Properties

    public boolean isBackDoublePressed() {
        return isBackDoublePressed;
    }

    public AuthViewModel setBackDoublePressed(boolean backDoublePressed) {
        isBackDoublePressed = backDoublePressed;
        return this;
    }


    //endregion

    //region Constructors

    @Inject
    public AuthViewModel() {
        isBackDoublePressed = false;
    }

    //endregion

    //region Methods


    //endregion

}
