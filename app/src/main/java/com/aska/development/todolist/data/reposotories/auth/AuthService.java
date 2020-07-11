package com.aska.development.todolist.data.reposotories.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aska.development.todolist.domain.interactors.SignInUseCase;
import com.aska.development.todolist.domain.interactors.SignOutUseCase;
import com.aska.development.todolist.domain.interactors.SignUpUseCase;
import com.aska.development.todolist.domain.model.Profile;

/**
 * Абстракция над конкретной реализацией<br>
 * */
public interface AuthService extends SignInUseCase.Executor, SignUpUseCase.Executor, SignOutUseCase.Executor {


    @Nullable
    Profile getProfile();

    @NonNull
    AuthService addProfileStateListener(@NonNull ProfileStateListener stateListener);


    interface ProfileStateListener{

        void onSigned();

        void onSignOut();

    }
}
