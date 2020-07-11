package com.aska.development.todolist.ui.main.tasks.edit;

import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.reposotories.main.MainRepository;

import javax.inject.Inject;

public class TaskEditViewModel extends ViewModel {
    //region Fields

    private MainRepository mRepository;

    //endregion

    //region Properties

    //endregion

    //region Constructors

    @Inject
    public TaskEditViewModel(MainRepository repository) {
        mRepository = repository;
    }

    //endregion

    //region Methods

    public void updateTask(){

    }

    //endregion

}
