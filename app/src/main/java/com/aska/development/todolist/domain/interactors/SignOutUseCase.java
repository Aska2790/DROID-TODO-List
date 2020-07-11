package com.aska.development.todolist.domain.interactors;

public class SignOutUseCase extends BaseUseCase {
    //region Fields

    private Executor mExecutor;

    //endregion

    //region Properties

    public SignOutUseCase setExecutor(Executor executor) {
        mExecutor = executor;
        return this;
    }

    //endregion

    //region Methods

    @Override
    protected void process() throws Exception {
        if(mExecutor == null){
            throw new NullPointerException();
        }

        mExecutor.signOut(new Callback() {
            @Override
            public void onSuccess() {
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
        void signOut(Callback callback) throws Exception;
    }

    public interface Callback {

        void onSuccess();

        void onFailure(Throwable throwable);
    }

    //endregion
}
