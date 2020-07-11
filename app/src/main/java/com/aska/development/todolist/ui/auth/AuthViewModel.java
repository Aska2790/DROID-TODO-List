package com.aska.development.todolist.ui.auth;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    //region Fields

    //endregion

    //region Properties
    //endregion

    //region Constructors

    @Inject
    public AuthViewModel() {

    }

    //endregion

    //region Methods

    public void singIn(String username, String password) {

    }

    public void singUp(String username, String password) {

    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {

        } else if (!isPasswordValid(password)) {

        } else {

        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
    //endregion

}
