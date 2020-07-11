package com.aska.development.todolist.di.auth;


import com.aska.development.todolist.ui.auth.signin.SignInFragment;
import com.aska.development.todolist.ui.auth.signup.SignUpFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AuthFragmentBuilderModule {

    @ContributesAndroidInjector()
    public abstract SignInFragment contributeSignInFragment();

    @ContributesAndroidInjector()
    public abstract SignUpFragment contributeSignUpFragment();

}