package com.aska.development.todolist.di.auth;


import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.di.general.ViewModelKey;
import com.aska.development.todolist.ui.auth.AuthViewModel;
import com.aska.development.todolist.ui.auth.signin.SignInViewModel;
import com.aska.development.todolist.ui.auth.signup.SignUpViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel.class)
    abstract ViewModel bindSignInViewModel(SignInViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpViewModel(SignUpViewModel viewModel);
}
