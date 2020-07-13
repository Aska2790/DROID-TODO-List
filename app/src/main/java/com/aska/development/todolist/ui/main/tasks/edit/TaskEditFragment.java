package com.aska.development.todolist.ui.main.tasks.edit;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.TaskEditBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;
import com.aska.development.todolist.ui.auxiliary.BackPressListener;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TaskEditFragment extends DaggerFragment implements BackPressListener {

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;

    private TaskEditBinding mBinding;
    private TaskEditViewModel mViewModel;
    private MenuItem mSaveMenuItem;

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save_action, menu);
        mSaveMenuItem = menu.findItem(R.id.save_menu_item);
        mSaveMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        updateSaveMenuItemState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == mSaveMenuItem.getItemId()) {
            onUpdate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Initialization

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(getActivity(), viewModelProvider).get(TaskEditViewModel.class);
        String taskId = TaskEditFragmentArgs.fromBundle(getArguments()).getTaskId();
        mBinding.setViewmodel(mViewModel);
        mViewModel.getTaskItem(taskId).observe(getViewLifecycleOwner(), taskItemViewModel -> {
            mBinding.setItem(taskItemViewModel);
            mBinding.invalidateAll();
        });

        mViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state != null) {
                switch (state.status) {
                    case ERROR: {
                        mBinding.requestIndicator.setVisibility(View.GONE);
                        break;
                    }
                    case SUCCESS: {
                        mBinding.requestIndicator.setVisibility(View.GONE);
                        NavController navController = NavHostFragment.findNavController(TaskEditFragment.this);
                        navController.popBackStack();
                        break;
                    }
                    case LOADING: {
                        mBinding.requestIndicator.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
    }

    private void initializeActionBar() {
        setHasOptionsMenu(true);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {

            if (!supportActionBar.isShowing()) {
                supportActionBar.show();
            }

            supportActionBar.setTitle(R.string.task_edit_dest_title);
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void initializeAction() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mBinding.invalidateAll();
                updateSaveMenuItemState();
            }
        };

        View.OnFocusChangeListener focusChangeListener = (view, b) -> {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        };

        mBinding.taskTitle.setOnFocusChangeListener(focusChangeListener);
        mBinding.taskDescription.setOnFocusChangeListener(focusChangeListener);

        mBinding.taskTitle.addTextChangedListener(textWatcher);
        mBinding.taskDescription.addTextChangedListener(textWatcher);


    }
    //endregion

    //region Action

    private void onUpdate() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.task_edit_update_dialog_title)
                .setNegativeButton(R.string.dialog_neg_button_title, null)
                .setPositiveButton(R.string.dialog_pos_button_title, (dialog, which) -> {
                    mViewModel.updateTask();
                })
                .create()
                .show();
    }

    private void updateSaveMenuItemState() {
        if (mSaveMenuItem != null) {
            if (mViewModel == null) {
                mSaveMenuItem.setVisible(false);
                return;
            }
            mSaveMenuItem.setVisible(mViewModel.isValid());
        }
    }

    @Override
    public boolean onBackPressEvent() {
        if (mViewModel.isValid()) {
            onUpdate();
            return true;
        }
        return false;
    }
    //endregion
}