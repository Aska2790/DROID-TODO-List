package com.aska.development.todolist.ui.auth;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.aska.development.todolist.R;
import com.aska.development.todolist.databinding.AuthViewBinding;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    //region Fields

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
        if (navController.getCurrentDestination().getId() != R.id.sign_in) {
            if (navController.popBackStack()) {
                return;
            }
        }
        super.onBackPressed();
    }
    //endregion

}