package com.aska.development.todolist.domain.interactors;

import com.aska.development.todolist.domain.model.Task;

public class AddTaskUseCase extends BaseUseCase {
    //region Fields

    private Task mTask;
    private Executor mExecutor;

    //endregion

    //region Properties

    public AddTaskUseCase setTask(Task task) {
        mTask = task;
        return this;
    }

    public AddTaskUseCase setExecutor(Executor executor) {
        mExecutor = executor;
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

        mExecutor.addTask(mTask, new Callback() {
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
        void addTask(Task task, Callback callback);
    }

    public interface Callback{
        void onSuccess();

        void onFailure(Throwable throwable);
    }
    //endregion
}
