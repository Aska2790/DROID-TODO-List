package com.aska.development.todolist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.aska.development.todolist.R;
import com.aska.development.todolist.di.general.FactoryViewModelProvider;
import com.aska.development.todolist.ui.auth.AuthActivity;
import com.aska.development.todolist.ui.auxiliary.BackPressListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;


public class MainActivity extends DaggerAppCompatActivity {

    //region Fields
    @Inject
    FactoryViewModelProvider viewModelProvider;
    private NavController navController;
    private NavHostFragment mHost;
    private MainViewModel mViewModel;

    //endregion

    //region Lifecycle
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActionBar();
        initializeNavigation();
        initializeViewModel();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    public void onBackPressed() {

        Fragment currentFragment = mHost.getChildFragmentManager().getFragments().get(0);
        if ((currentFragment instanceof BackPressListener) && ((BackPressListener) currentFragment).onBackPressEvent()) {
            return;
        }

        if (!navController.popBackStack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    //endregion

    //region Initialization

    private void initializeActionBar() {
        setSupportActionBar(findViewById(R.id.main_toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initializeNavigation() {
        mHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host_fragment);
        navController = mHost.getNavController();

        setupActionBarWithNavController(this, navController, new AppBarConfiguration.Builder(navController.getGraph()).build());
        setupWithNavController((BottomNavigationView) findViewById(R.id.bottom_nav_view), navController);
    }

    private void initializeViewModel() {
        mViewModel = new ViewModelProvider(this, viewModelProvider).get(MainViewModel.class);
        mViewModel.isAuthorized().observe(this, authorized -> {
            if (authorized != null && !authorized) {
                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
//endregion
}


