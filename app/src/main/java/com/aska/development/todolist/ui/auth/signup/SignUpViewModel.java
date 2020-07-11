package com.aska.development.todolist.ui.auth.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aska.development.todolist.data.auxiliary.Result;
import com.aska.development.todolist.data.auxiliary.SingleLiveEvent;
import com.aska.development.todolist.data.reposotories.auth.AuthService;
import com.aska.development.todolist.domain.interactors.SignUpUseCase;
import com.aska.development.todolist.domain.interfaces.AsyncActionListener;
import com.aska.development.todolist.ui.auxiliary.Validator;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {
    //region Fields

    private AuthService mAuthService;
    private SingleLiveEvent<Result<Object>> mState;
    private MutableLiveData<String> mFirstName;
    private MutableLiveData<String> mSecondName;
    private MutableLiveData<String> mEmail;
    private MutableLiveData<String> mPassword;
    private MutableLiveData<String> mControlPassword;
    private MutableLiveData<String> mErrorMessage;
    //endregion

    //region Properties

    public LiveData<Result<Object>> getState() {
        return mState;
    }

    public MutableLiveData<String> getFirstName() {
        return mFirstName;
    }

    public MutableLiveData<String> getSecondName() {
        return mSecondName;
    }

    public MutableLiveData<String> getEmail() {
        return mEmail;
    }

    public MutableLiveData<String> getPassword() {
        return mPassword;
    }

    public MutableLiveData<String> getControlPassword() {
        return mControlPassword;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    //endregion

    //region Constructors

    @Inject
    public SignUpViewModel(AuthService authService) {
        mAuthService = authService;
        mState = new SingleLiveEvent<>();
        mFirstName = new MutableLiveData<>();
        mSecondName = new MutableLiveData<>();
        mEmail = new MutableLiveData<>();
        mPassword = new MutableLiveData<>();
        mControlPassword = new MutableLiveData<>();
        mErrorMessage = new MutableLiveData<>();
    }

    //endregion

    //region Methods
    public void signUp() {
        mState.postValue(Result.loading(null));
        mErrorMessage.postValue(null);
        SignUpUseCase.Param param = new SignUpUseCase.Param()
                .setFirstName(mFirstName.getValue())
                .setSecondName(mSecondName.getValue())
                .setEmail(mEmail.getValue())
                .setPassword(mPassword.getValue());

        new SignUpUseCase()
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

        if(!Validator.isEmailValid(mEmail.getValue())){
            return false;
        }

        if(!Validator.isPasswordValid(mPassword.getValue())){
            return false;
        }

        if(!Validator.isPasswordValid(mControlPassword.getValue())){
            return false;
        }

        if(!Validator.isPlainTextValid(mFirstName.getValue())){
            return false;
        }

        if(!Validator.isPlainTextValid(mSecondName.getValue())){
            return false;
        }

        String controlPasswordValue = mControlPassword.getValue();
        String passwordValue = mPassword.getValue();

        if(!passwordValue.equals(controlPasswordValue)){
            return false;
        }
        return true;


    }


    //endregion

}
