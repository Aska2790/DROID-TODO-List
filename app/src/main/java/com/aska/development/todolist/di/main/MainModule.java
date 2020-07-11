package com.aska.development.todolist.di.main;

import com.aska.development.todolist.data.db.LocalDB;
import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.data.reposotories.main.MainRepository;
import com.aska.development.todolist.data.reposotories.main.MainRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @MainScope
    @Provides
    public static MainRepository provideTaskRepository(AuthService authService, LocalDB db) {
        return new MainRepositoryImpl(authService, db.getTaskDao());
    }

}
