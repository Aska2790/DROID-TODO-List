package com.aska.development.todolist.domain.interactors;

public class DeleteTaskUseCase extends BaseUseCase {
    //region Fields

    private String mTaskId;
    private Executor mExecutor;

    //endregion

    //region Properties


    public DeleteTaskUseCase setTaskId(String taskId) {
        mTaskId = taskId;
        return this;
    }

    public DeleteTaskUseCase setExecutor(Executor executor) {
        mExecutor = executor;
        return this;
    }

    //endregion

    //region Methods

    @Override
    protected void process() throws Exception {
        if(mTaskId == null || mTaskId.equals("")){
            throw new NullPointerException();
        }

        if(mExecutor == null){
            throw new NullPointerException();
        }

        mExecutor.deleteTask(mTaskId, new Callback() {
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

    public interface Executor{
        void deleteTask(String taskId, Callback callback);
    }

    public interface Callback{
        void onSuccess();

        void onFailure(Throwable throwable);
    }
    //endregion
}
