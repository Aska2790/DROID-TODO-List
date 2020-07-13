package com.aska.development.todolist.data.reposotories.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aska.development.todolist.domain.interactors.SignInUseCase;
import com.aska.development.todolist.domain.interactors.SignOutUseCase;
import com.aska.development.todolist.domain.interactors.SignUpUseCase;
import com.aska.development.todolist.domain.model.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    //region Fields

    private FirebaseAuth mAuth;
    private Profile mProfile;
    private List<ProfileStateListener> mProfileStateListeners;
    //endregion

    //region Properties

    @Override
    @Nullable
    public Profile getProfile() {
        return mProfile;
    }

    @NonNull
    @Override
    public AuthService addProfileStateListener(@NonNull ProfileStateListener value) {
        value.getClass();
        if(mProfileStateListeners == null){
            mProfileStateListeners = new ArrayList<>();
        }
        mProfileStateListeners.add(value);
        return this;
    }

    //endregion

    //region Constructors

    public AuthServiceImpl() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this::onAuthStateChanged);
    }

    //endregion

    //region Methods


    @Override
    public void signIn(@NonNull SignInUseCase.Param param, @NonNull SignInUseCase.Callback callback) throws Exception {

        param.getEmail().getClass();
        param.getPassword().getClass();
        callback.getClass();

        mAuth.signInWithEmailAndPassword(param.getEmail(), param.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onAuthStateChanged(mAuth);
                        callback.onSigned();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    @Override
    public void signUp(@NonNull SignUpUseCase.Param param, @NonNull SignUpUseCase.Callback callback) throws Exception {
        param.getEmail().getClass();
        param.getPassword().getClass();
        callback.getClass();

        mAuth.createUserWithEmailAndPassword(param.getEmail(), param.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onCreated();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    @Override
    public void signOut(SignOutUseCase.Callback callback) throws Exception {
        callback.getClass();
        mAuth.signOut();
        callback.onSuccess();
    }

    private void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mProfile = new Profile()
                    .setUID(currentUser.getUid());

            if(mProfileStateListeners != null){
                for(ProfileStateListener listener :mProfileStateListeners){
                    listener.onSigned();
                }
            }
        } else {
            mProfile = null;
            if(mProfileStateListeners != null){
                for(ProfileStateListener listener :mProfileStateListeners){
                    listener.onSignOut();
                }
                mProfileStateListeners.clear();
            }
        }
    }

    //endregion

}
