package com.aska.development.todolist.data.auxiliary;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Result<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public Result(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Result<T> success (@Nullable T data) {
        return new Result<>(Status.SUCCESS, data, null);
    }

    public static <T> Result<T> success (@Nullable T data, String message) {
        return new Result<>(Status.SUCCESS, data, message);
    }

    public static <T> Result<T> error(@NonNull String msg, @Nullable T data) {
        return new Result<>(Status.ERROR, data, msg);
    }

    public static <T> Result<T> loading(@Nullable T data) {
        return new Result<>(Status.LOADING, data, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING}
}