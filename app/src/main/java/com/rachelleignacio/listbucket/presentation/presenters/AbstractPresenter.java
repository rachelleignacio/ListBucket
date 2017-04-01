package com.rachelleignacio.listbucket.presentation.presenters;

import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public abstract class AbstractPresenter {
    protected Executor executor;
    protected MainThread mainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        this.executor = executor;
        this.mainThread = mainThread;
    }
}
