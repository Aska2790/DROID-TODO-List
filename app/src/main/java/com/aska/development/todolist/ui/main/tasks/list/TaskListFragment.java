package com.aska.development.todolist.ui.main.tasks.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.TaskListViewBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;
import com.aska.development.todolist.ui.auxiliary.RecyclerClickListener;
import com.aska.development.todolist.ui.auxiliary.RecyclerTouchListenerImpl;
import com.aska.development.todolist.ui.main.tasks.TaskItemViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TaskListFragment extends DaggerFragment implements RecyclerClickListener {

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;

    private TaskListViewBinding mBinding;
    private TaskItemViewAdapter mAdapter;
    private TaskListViewModel mViewModel;

    //endregion

    //region Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle s) {
        mBinding = TaskListViewBinding.inflate(inflater);
        initializeAdapter();
        initializeViewModel();
        initializeActionBar();
        initializeAction();
        initializeRecyclerView();
        return mBinding.getRoot();
    }

    //endregion

    //region Initialization

    private void initializeAdapter() {
        mAdapter = new TaskItemViewAdapter();
    }

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(getActivity(), viewModelProvider).get(TaskListViewModel.class);
        mViewModel.getTaskItemViewModels().observe(getViewLifecycleOwner(), taskItemViewModels -> {
            mAdapter.setItems(taskItemViewModels);
        });
    }

    private void initializeActionBar() {
        setHasOptionsMenu(true);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {

            if (!supportActionBar.isShowing()) {
                supportActionBar.show();
            }

            supportActionBar.setTitle(R.string.task_list_dest_title);
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setDisplayShowHomeEnabled(false);
        }
    }

    private void initializeAction() {
        mBinding.fab.setOnClickListener(this::onAddTask);
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = mBinding.recyclerView;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListenerImpl(getActivity(), recyclerView, this));

        if (mAdapter != null) {
            recyclerView.setAdapter(mAdapter);
        }
    }

    //endregion

    //region Action

    public void onClick(View view, int position) {
        TaskItemViewModel item = mAdapter.getItem(position);
        if (item != null) {
            NavController navController = NavHostFragment.findNavController(TaskListFragment.this);
            TaskListFragmentDirections.TaskViewAction viewAction = TaskListFragmentDirections.taskViewAction(item.getId());
            navController.navigate(viewAction);
        }
    }

    public void onLongClick(View view, int position) {

    }

    private void onAddTask(View view) {
        NavController navController = NavHostFragment.findNavController(TaskListFragment.this);
        NavDirections addAction = TaskListFragmentDirections.taskAddAction();
        navController.navigate(addAction);
    }

    //endregion
}