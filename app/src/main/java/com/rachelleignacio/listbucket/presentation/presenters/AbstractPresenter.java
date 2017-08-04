package com.rachelleignacio.listbucket.presentation.presenters;

import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public abstract class AbstractPresenter {
    protected ThreadExecutor threadExecutor;
    protected MainThread mainThread;

    public AbstractPresenter(ThreadExecutor threadExecutor, MainThread mainThread) {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;
    }
}
