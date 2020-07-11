package com.aska.development.todolist.di.general;

import com.aska.development.todolist.di.auth.AuthFragmentBuilderModule;
import com.aska.development.todolist.di.auth.AuthModule;
import com.aska.development.todolist.di.auth.AuthScope;
import com.aska.development.todolist.di.auth.AuthViewModelModule;
import com.aska.development.todolist.di.main.MainFragmentBuilderModule;
import com.aska.development.todolist.di.main.MainModule;
import com.aska.development.todolist.di.main.MainScope;
import com.aska.development.todolist.di.main.MainViewModelModule;
import com.aska.development.todolist.ui.auth.AuthActivity;
import com.aska.development.todolist.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = {
            AuthViewModelModule.class,
            AuthFragmentBuilderModule.class,
            AuthModule.class
    })
    public abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainViewModelModule.class,
            MainFragmentBuilderModule.class,
            MainModule.class
    })
    public abstract MainActivity contributeTaskActivity();
}
