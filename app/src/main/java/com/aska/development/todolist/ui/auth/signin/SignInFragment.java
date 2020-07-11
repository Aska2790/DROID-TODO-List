package com.aska.development.todolist.ui.auth.signin;

import android.content.Intent;
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
import androidx.navigation.Navigation;

import com.aska.development.todolist.databinding.SignInViewBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;
import com.aska.development.todolist.ui.main.MainActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class SignInFragment extends DaggerFragment {

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;
    private SignInViewBinding mBinding;
    private SignInViewModel mViewModel;

    //endregion

    //region Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = SignInViewBinding.inflate(inflater);
        mBinding.setLifecycleOwner(this);
        initializeViewModel();
        initializeActionBar();
        initializeAction();
        return mBinding.getRoot();
    }
    //endregion

    //region Initialization

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(this, viewModelProvider).get(SignInViewModel.class);
        mBinding.setViewmodel(mViewModel);
        mViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            if (state != null) {
                switch (state.status) {
                    case LOADING: {
                        mBinding.loading.setVisibility(View.VISIBLE);

                        break;
                    }
                    case SUCCESS: {
                        mBinding.loading.setVisibility(View.GONE);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    }
                    case ERROR: {
                        mBinding.loading.setVisibility(View.GONE);
                        break;
                    }
                }
            }
            mBinding.invalidateAll();
        });
    }

    private void initializeActionBar() {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            if (supportActionBar.isShowing()) {
                supportActionBar.hide();
            }

        }
    }

    private void initializeAction() {
        mBinding.signUp.setOnClickListener(this::onSignUp);
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

        mBinding.authEmail.addTextChangedListener(textWatcher);
        mBinding.authPassword.addTextChangedListener(textWatcher);
    }


    //endregion

    //region Action


    private void onSignUp(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(SignInFragmentDirections.signUpAction());
    }

    //endregion

}