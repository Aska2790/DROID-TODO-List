package com.aska.development.todolist.domain.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface AsyncActionListener {

    void onSuccess(@Nullable Object object);

    void onError(@NonNull Throwable throwable);

}
