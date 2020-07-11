package com.aska.development.todolist.domain.interactors;

import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.aska.development.todolist.domain.interfaces.AsyncActionListener;

public abstract class BaseUseCase extends AsyncTask<Void, Void, Void> {
    //region Fields
    protected AsyncActionListener mListener;
    //endregion

    //region Properties

    public BaseUseCase setListener(@Nullable AsyncActionListener listener) {
        mListener = listener;
        return this;
    }

    //endregion

    //region Methods

    protected abstract void process() throws Exception;

    protected Void doInBackground(Void... voids) {
        try {
            process();
        } catch (Throwable throwable) {
            onFailureResult(throwable);
        }
        return null;
    }

    public void onSuccessResult() {
        if(mListener != null){
            mListener.onSuccess(null);
        }
    }

    public void onFailureResult(Throwable throwable) {
        if(mListener != null){
            mListener.onError(throwable);
        }
    }

    //endregion
}
