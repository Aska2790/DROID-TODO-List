package com.aska.development.todolist.di.general;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindVMPFactory(FactoryViewModelProvider factoryViewModelProvider);

}
