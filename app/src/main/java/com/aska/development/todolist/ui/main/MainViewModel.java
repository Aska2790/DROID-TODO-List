package com.aska.development.todolist.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.data.reposotories.main.MainRepository;
import com.aska.development.todolist.domain.model.Profile;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    //region Fields

    private AuthService mAuthService;
    private MainRepository mRepository;
    private MutableLiveData<Boolean> isAuthorized;

    //endregion

    //region Properties

    public Profile getProfile(){
        return mAuthService.getProfile();
    }

    public LiveData<Boolean> isAuthorized() {
        return isAuthorized;
    }

    //endregion

    //region Constructors

    @Inject
    public MainViewModel(AuthService authService, MainRepository repository) {

        mAuthService = authService;
        mRepository = repository;
        isAuthorized = new MutableLiveData<>(true);
        mAuthService.addProfileStateListener(new AuthService.ProfileStateListener() {
            @Override
            public void onSigned() {
                isAuthorized.postValue(true);
            }

            @Override
            public void onSignOut() {
                isAuthorized.postValue(false);
            }
        });
    }

    //endregion

    //region Methods

    //endregion

    //region Inner 
    //endregion
}
