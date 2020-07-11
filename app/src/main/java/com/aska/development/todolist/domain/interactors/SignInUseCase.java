package com.aska.development.todolist.domain.interactors;

public class SignInUseCase extends BaseUseCase {

    //region Fields

    private Param mParam;
    private Executor mExecutor;

    //endregion

    //region Properties

    public SignInUseCase setParam(Param value) {
        mParam = value;
        return this;
    }

    public SignInUseCase setExecutor(Executor executor) {
        mExecutor = executor;
        return this;
    }

    //endregion

    //region Methods

    @Override
    protected void process() throws Exception {
        if(mExecutor == null){
            throw new NullPointerException("sign in api is null");
        }

        if(mParam == null){
            throw new NullPointerException("sign in param is null");
        }

        mExecutor.signIn(mParam, new Callback() {
            @Override
            public void onSigned() {
                onSuccessResult();
            }

            @Override
            public void onFailure(Throwable throwable) {
                onFailureResult(throwable);
            }
        });
    }

    //endregion

    //region Inner

    public interface Executor {
        void signIn(Param param, Callback callback) throws Exception;
    }

    public interface Callback {

        void onSigned();

        void onFailure(Throwable throwable);
    }

    public static class Param {
        //region Fields

        private String mEmail;
        private String mPassword;

        //endregion

        //region Properties

        public String getEmail() {
            return mEmail;
        }

        public Param setEmail(String email) {
            mEmail = email;
            return this;
        }

        public String getPassword() {
            return mPassword;
        }

        public Param setPassword(String password) {
            mPassword = password;
            return this;
        }

        //endregion

    }

    //endregion


}
