package com.aska.development.todolist.ui.main.tasks.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

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
    private String mTaskId;

    //endregion

    //region Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle s) {
        mBinding = TaskViewBinding.inflate(inflater);
        initializeActionBar();
        initializeViewModel();
        initializeAction();
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.task_view_action, menu);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.task_edit)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.task_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.task_edit: {
                onEdit();
                return true;
            }
            case R.id.task_delete: {
                onDelete();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    //endregion

    //region Initialization

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(getActivity(), viewModelProvider).get(TaskViewViewModel.class);
        mTaskId = TaskViewFragmentArgs.fromBundle(getArguments()).getId();
        mViewModel.getTaskItem(mTaskId).observe(getViewLifecycleOwner(), taskItemViewModel -> {
            if(taskItemViewModel != null) {
                mBinding.setItem(taskItemViewModel);
                mBinding.invalidateAll();
            }
        });
        mViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state != null) {
                switch (state.status) {
                    case ERROR:{
                        mBinding.requestIndicator.setVisibility(View.GONE);
                        break;
                    }
                    case SUCCESS:{
                        mBinding.requestIndicator.setVisibility(View.GONE);
                        NavController navController = NavHostFragment.findNavController(TaskViewFragment.this);
                        navController.popBackStack();
                        break;
                    }
                    case LOADING:{
                        mBinding.requestIndicator.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
    }

    private void initializeActionBar() {
        setHasOptionsMenu(true);
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

    private void initializeAction() {

    }
    //endregion

    //region Action

    private void onDelete() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.task_view_delete_dialog_title)
                .setNegativeButton(R.string.dialog_neg_button_title, null)
                .setPositiveButton(R.string.dialog_pos_button_title, (dialog, which) -> {
                    mViewModel.deleteTask(mTaskId);
                })
                .create()
                .show();
    }

    private void onEdit() {
        TaskViewFragmentDirections.EditAction editAction = TaskViewFragmentDirections.editAction(mTaskId);
        NavController navController = NavHostFragment.findNavController(TaskViewFragment.this);
        navController.navigate(editAction);
    }


    //endregion
}