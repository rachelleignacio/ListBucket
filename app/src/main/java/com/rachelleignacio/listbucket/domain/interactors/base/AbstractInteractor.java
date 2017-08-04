package com.rachelleignacio.listbucket.domain.interactors.base;

import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor;

/**
 * Created by rachelleignacio on 3/6/17.
 * Abstract class that implements some common methods for all interactors, for example cancelling
 * an interactor, checking if it's running, and finishing an interactor.
 */

public abstract class AbstractInteractor implements Interactor {
    protected ThreadExecutor threadExecutor;
    protected MainThread mainThread;

    protected volatile boolean isCanceled;
    protected volatile boolean isRunning;

    public AbstractInteractor(ThreadExecutor threadExecutor, MainThread mainThread) {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
    }

    /**
     * This method contains the actual business logic of the interactor. It SHOULD NOT BE USED
     * DIRECTLY, but instead the execute() method of the interactor should be called to ensure the
     * operation is done on a background thread.
     */
    public abstract void run();

    public void cancel() {
        isCanceled = true;
        isRunning = false;
    }

    public void onFinished() {
        isRunning = false;
        isCanceled = false;
    }

    public void execute() {
        isRunning = true;
        threadExecutor.execute(this);
    }
}
