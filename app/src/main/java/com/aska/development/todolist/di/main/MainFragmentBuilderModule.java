package com.aska.development.todolist.di.main;


import com.aska.development.todolist.ui.main.profile.ProfileFragment;
import com.aska.development.todolist.ui.main.tasks.add.TaskAddFragment;
import com.aska.development.todolist.ui.main.tasks.edit.TaskEditFragment;
import com.aska.development.todolist.ui.main.tasks.list.TaskListFragment;
import com.aska.development.todolist.ui.main.tasks.view.TaskViewFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector()
    public abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector()
    public abstract TaskListFragment contributeTaskListFragment();

    @ContributesAndroidInjector()
    public abstract TaskViewFragment contributeTaskFragment();


    @ContributesAndroidInjector()
    public abstract TaskAddFragment contributeTaskAddFragment();


    @ContributesAndroidInjector()
    public abstract TaskEditFragment contributeTaskEditFragment();

}