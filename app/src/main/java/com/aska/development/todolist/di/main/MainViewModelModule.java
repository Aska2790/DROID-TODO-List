package com.aska.development.todolist.di.main;


import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.di.general.ViewModelKey;
import com.aska.development.todolist.ui.main.MainViewModel;
import com.aska.development.todolist.ui.main.profile.ProfileViewModel;
import com.aska.development.todolist.ui.main.tasks.list.TaskListViewModel;
import com.aska.development.todolist.ui.main.tasks.view.TaskViewViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewViewModel.class)
    abstract ViewModel bindTaskViewModel(TaskViewViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TaskListViewModel.class)
    abstract ViewModel bindTaskListViewModel(TaskListViewModel viewModel);

}
