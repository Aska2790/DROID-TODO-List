package com.aska.development.todolist.ui.main.profile;

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

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.ProfileViewBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProfileFragment extends DaggerFragment {

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;
    private ProfileViewBinding mBinding;
    private ProfileViewModel mViewModel;

    //endregion

    //region Lifecycle


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle s) {
        mBinding = ProfileViewBinding.inflate(inflater);
        initializeViewModel();
        initializeActionBar();
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_view_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.sign_out_action){
            onSignOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSignOut() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.profile_sign_out_dialog_title)
                .setNegativeButton(R.string.dialog_neg_button_title, null)
                .setPositiveButton(R.string.dialog_pos_button_title, (dialog, which) -> {
                    mViewModel.signOut();
                })
                .create()
                .show();
    }

    //endregion

    //region Initialization

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(getActivity(), viewModelProvider).get(ProfileViewModel.class);
        mBinding.setViewmodel(mViewModel);
        mViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if(state != null){
                switch (state.status){
                    case LOADING:
                    case SUCCESS:
                    case ERROR:{
                        break;
                    }
                }
                mBinding.invalidateAll();
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

            supportActionBar.setTitle(R.string.profile_dest_title);
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setDisplayShowHomeEnabled(false);
        }
    }

    //endregion

    //region Action


    //endregion
}