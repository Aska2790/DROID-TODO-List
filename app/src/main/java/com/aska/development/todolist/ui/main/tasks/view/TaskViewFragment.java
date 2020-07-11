package com.aska.development.todolist.ui.main.tasks.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.TaskViewBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TaskViewFragment extends DaggerFragment {

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;

    private TaskViewBinding mBinding;
    private TaskViewViewModel mViewModel;
    private ActionBar mSupportActionBar;

    //endregion

    //region Lifecycle


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle s) {
        mBinding = TaskViewBinding.inflate(inflater);
        initializeActionBar();
        return mBinding.getRoot();
    }

    //endregion

    //region Initialization

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(getActivity(), viewModelProvider).get(TaskViewViewModel.class);

    }

    private void initializeActionBar() {
        setHasOptionsMenu(false);
        mSupportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (mSupportActionBar != null) {

            if (!mSupportActionBar.isShowing()) {
                mSupportActionBar.show();
            }

            mSupportActionBar.setTitle(R.string.task_view_dest_title);
            mSupportActionBar.setHomeButtonEnabled(true);      // Disable the button
            mSupportActionBar.setDisplayHomeAsUpEnabled(true); // Remove the left caret
            mSupportActionBar.setDisplayShowHomeEnabled(true); // Remove the icon
        }
    }

    //endregion

    //region Action


    //endregion
}