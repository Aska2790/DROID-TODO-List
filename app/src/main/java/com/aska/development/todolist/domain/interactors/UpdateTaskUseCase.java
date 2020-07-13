package com.aska.development.todolist.domain.interactors;

import com.aska.development.todolist.domain.model.Task;

public class UpdateTaskUseCase extends BaseUseCase {
    //region Fields
    private Executor mExecutor;
    private Task mTask;
    //endregion

    //region Properties

    public UpdateTaskUseCase setExecutor(Executor executor) {
        mExecutor = executor;
        return this;
    }

    public UpdateTaskUseCase setTask(Task task) {
        mTask = task;
        return this;
    }

    //endregion

    //region Methods

    @Override
    protected void process() throws Exception {
        if(mTask == null){
            throw new NullPointerException();
        }

        if(mExecutor == null){
            throw new NullPointerException();
        }

        mExecutor.updateTask(mTask, new Callback() {
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
        void updateTask(Task task, Callback callback);
    }

    public interface Callback{
        void onSuccess();

        void onFailure(Throwable throwable);
    }

    //endregion
}
