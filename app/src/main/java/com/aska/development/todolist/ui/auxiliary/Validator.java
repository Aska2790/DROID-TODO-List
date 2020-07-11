package com.aska.development.todolist.ui.auxiliary;

import android.util.Patterns;

public class Validator {

    public static boolean isPasswordValid(String value){
        return value != null && value.trim().length() > 5;
    }

    public static boolean isPlainTextValid(String value){
        return value != null && !value.equals("");
    }

    public static boolean isEmailValid(String value){
        if (value == null) {
            return false;
        }
        if (value.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(value).matches();
        } else {
            return !value.trim().isEmpty();
        }
    }
}
