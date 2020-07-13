package com.aska.development.todolist.ui.auth;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.AuthViewBinding;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {
    public static final int BACK_PRESS_DELAY_MILLIS = 2000;

    //region Fields

    @Inject
    FactoryViewModelProvider viewModelProvider;
    private AuthViewModel mViewModel;
    private AuthViewBinding mBinding;
    private NavController navController;
    //endregion

    //region Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        setSupportActionBar(mBinding.mainToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.second_nav_host_fragment);
        navController =  navHostFragment.getNavController();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    public void onBackPressed() {

        if (mViewModel != null && mViewModel.isBackDoublePressed()) {
            finish();
            System.exit(0);
            return;
        }

        if (navController.getCurrentDestination().getId() != R.id.sign_in) {
            if (navController.popBackStack()) {
                return;
            }
        }

        if(mViewModel != null){
            mViewModel.setBackDoublePressed(true);
            Toast.makeText(this, R.string.main_first_back_click_message,
                    Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mViewModel.setBackDoublePressed(false);
                }
            }, BACK_PRESS_DELAY_MILLIS);
        }
    }
    //endregion

    public void initializeViewModel(){
        mViewModel = new ViewModelProvider(this, viewModelProvider).get(AuthViewModel.class);
    }

}