package com.aska.development.todolist.ui.auth.signin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.auxiliary.Result;
import com.aska.development.todolist.data.auxiliary.SingleLiveEvent;
import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.domain.interactors.SignInUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.ui.auxiliary.Validator;

import javax.inject.Inject;

public class SignInViewModel extends ViewModel {

    //region Fields

    private AuthService mAuthService;
    private SingleLiveEvent<Result<Object>> mState;
    private MutableLiveData<String> mEmail;
    private MutableLiveData<String> mPassword;
    private MutableLiveData<String> mErrorMessage;
    //endregion

    //region Properties

    public MutableLiveData<String> getEmail() {
        return mEmail;
    }

    public MutableLiveData<String> getPassword() {
        return mPassword;
    }

    public LiveData<Result<Object>> getState() {
        return mState;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }


    //endregion

    //region Constructors

    @Inject
    public SignInViewModel(AuthService authService) {
        mAuthService = authService;
        mState = new SingleLiveEvent<>();
        mEmail = new MutableLiveData<>();
        mPassword = new MutableLiveData<>();
        mErrorMessage = new MutableLiveData<>();
    }

    //endregion

    //region Methods

    public void signIn(){
        mErrorMessage.postValue(null);
        mState.postValue(Result.loading(null));
        SignInUseCase.Param param = new SignInUseCase.Param()
                .setEmail(mEmail.getValue())
                .setPassword(mPassword.getValue());

        new SignInUseCase()
                .setExecutor(mAuthService)
                .setParam(param)
                .setListener(new AsyncActionListener() {
                    @Override
                    public void onSuccess(@Nullable Object object) {
                        mState.postValue(Result.success(object));
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        mErrorMessage.postValue(throwable.getMessage());
                        mState.postValue(Result.error(throwable.getMessage(), null));
                    }
                })
                .execute();
    }

    public boolean isParamValid(){
        return Validator.isEmailValid(mEmail.getValue()) && Validator.isPasswordValid(mPassword.getValue());
    }


    //endregion

}
