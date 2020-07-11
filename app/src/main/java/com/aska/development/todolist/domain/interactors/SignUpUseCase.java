package com.aska.development.todolist.domain.interactors;

public class SignUpUseCase extends BaseUseCase{
    //region Fields

    private Param mParam;
    private Executor mExecutor;

    //endregion

    //region Properties

    public SignUpUseCase setParam(Param param) {
        mParam = param;
        return this;
    }

    public SignUpUseCase setExecutor(Executor executor) {
        mExecutor = executor;
        return this;
    }

    //endregion

    //region Methods

    @Override
    protected void process() throws Exception {
        Thread.sleep(2000);
        if(mExecutor == null){
            throw new NullPointerException("sign up api is null");
        }

        if(mParam == null){
            throw new NullPointerException("sign up param is null");
        }

        mExecutor.signUp(mParam, new Callback() {
            @Override
            public void onCreated() {
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

    public static interface Executor {
        void signUp(Param param, Callback callback) throws Exception;
    }

    public static class Param {
        //region Fields

        private String mEmail;
        private String mPassword;
        private String mFirstName;
        private String mSecondName;

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

        public String getFirstName() {
            return mFirstName;
        }

        public Param setFirstName(String firstName) {
            mFirstName = firstName;
            return this;
        }

        public String getSecondName() {
            return mSecondName;
        }

        public Param setSecondName(String secondName) {
            mSecondName = secondName;
            return this;
        }

        //endregion

    }

    public static interface Callback {

        void onCreated();

        void onFailure(Throwable throwable);
    }

    //endregion
}
