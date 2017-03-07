package com.rachelleignacio.listbucket.executor;

import com.rachelleignacio.listbucket.interactors.base.AbstractInteractor;

/**
 * Created by rachelleignacio on 3/7/17.
 * Executor responsible for running interactors on background threads.
 */
public interface Executor {
    void execute(final AbstractInteractor interactor);
}
