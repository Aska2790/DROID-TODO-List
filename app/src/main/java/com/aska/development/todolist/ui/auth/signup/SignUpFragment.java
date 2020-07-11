package com.aska.development.todolist.ui.auth.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.SignUpViewBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class SignUpFragment extends DaggerFragment {

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;
    private SignUpViewBinding mBinding;
    private SignUpViewModel mViewModel;

    //endregion

    //region Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = SignUpViewBinding.inflate(inflater);
        initializeViewModel();
        initializeActionBar();
        initializeAction();
        return mBinding.getRoot();
    }

    //endregion

    //region Initialization

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(this, viewModelProvider).get(SignUpViewModel.class);
        mBinding.setViewmodel(mViewModel);
        mViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if(state != null){
                switch (state.status){
                    case LOADING:{
                        mBinding.loading.setVisibility(View.VISIBLE);
                        break;
                    }
                    case SUCCESS:{
                        mBinding.loading.setVisibility(View.GONE);
                        NavController navController = NavHostFragment.findNavController(SignUpFragment.this);
                        navController.popBackStack();
                        break;
                    }
                    case ERROR:{
                        mBinding.loading.setVisibility(View.GONE);
                        break;
                    }
                }
                mBinding.invalidateAll();
            }
        });
    }

    private void initializeActionBar() {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            if (!supportActionBar.isShowing()) {
                supportActionBar.show();
            }

            supportActionBar.setTitle(R.string.sign_up_dest_title);
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
            }
        };
        mBinding.authFirstName.addTextChangedListener(textWatcher);
        mBinding.authSecondName.addTextChangedListener(textWatcher);
        mBinding.authEmail.addTextChangedListener(textWatcher);
        mBinding.authPassword.addTextChangedListener(textWatcher);
        mBinding.authControlPassword.addTextChangedListener(textWatcher);

    }


    //endregion

    //region Action



    //endregion
}