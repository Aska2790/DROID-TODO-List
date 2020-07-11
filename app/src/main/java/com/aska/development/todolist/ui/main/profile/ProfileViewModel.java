package com.aska.development.todolist.ui.main.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.auxiliary.Result;
import com.aska.development.todolist.data.auxiliary.SingleLiveEvent;
import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.domain.interactors.SignOutUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.domain.model.Profile;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    //region Fields

    private MutableLiveData<String> mFirstName;
    private MutableLiveData<String> mSecondName;

    private AuthService mAuthService;
    private SingleLiveEvent<Result<Object>> mState;

    //endregion

    //region Properties

    public SingleLiveEvent<Result<Object>> getState() {
        return mState;
    }

    //endregion

    //region Constructors

    @Inject
    public ProfileViewModel(AuthService authService) {
        mAuthService = authService;
        mState = new SingleLiveEvent<>();
        mFirstName = new MutableLiveData<>();
        mSecondName = new MutableLiveData<>();
    }

    //endregion

    //region Methods

    public void signOut() {
        mState.postValue(Result.loading(null));
        new SignOutUseCase()
                .setExecutor(mAuthService)
                .setListener(new AsyncActionListener() {
                    @Override
                    public void onSuccess(@Nullable Object object) {
                        mState.postValue(Result.success(object));
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        mState.postValue(Result.error(throwable.getMessage(), null));
                    }
                })
                .execute();
    }

    public void editProfileInfo(){

    }

    public void updateProfileInfo(){
        Profile profile = mAuthService.getProfile();

        mFirstName.postValue(profile.getFirstName());
        mSecondName.postValue(profile.getSecondName());
    }
    //endregion

}
