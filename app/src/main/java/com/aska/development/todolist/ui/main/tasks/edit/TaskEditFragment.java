package com.aska.development.todolist.ui.main.tasks.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.TaskEditBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TaskEditFragment extends DaggerFragment {

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;

    private TaskEditBinding mBinding;
    private TaskEditViewModel mViewModel;

    //endregion

    //region Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle s) {
        mBinding = TaskEditBinding.inflate(inflater);
        initializeViewModel();
        initializeActionBar();
        initializeAction();
        return mBinding.getRoot();
    }

    //endregion

    //region Initialization

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(getActivity(), viewModelProvider).get(TaskEditViewModel.class);
    }

    private void initializeActionBar() {
        setHasOptionsMenu(true);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {

            if (!supportActionBar.isShowing()) {
                supportActionBar.show();
            }

            supportActionBar.setTitle(R.string.task_edit_dest_title);
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setDisplayShowHomeEnabled(false);
        }
    }

    private void initializeAction() {

    }

    //endregion

    //region Action

    private void onUpdate(View view) {

    }

    //endregion
}