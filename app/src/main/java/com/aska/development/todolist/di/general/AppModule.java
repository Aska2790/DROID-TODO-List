package com.aska.development.todolist.di.general;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.aska.development.todolist.data.db.LocalDB;
import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.data.reposotories.auth.AuthServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public static Context provideAppContext(Application application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    public static LocalDB provideLocalDB(Application application) {
        return Room.databaseBuilder(application, LocalDB.class, "todo_list_db").build();
    }

    @Singleton
    @Provides
    public static AuthService provideAuthService() {
        return new AuthServiceImpl();
    }
}
