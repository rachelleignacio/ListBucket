package com.rachelleignacio.listbucket.domain.executor;

import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor;

/**
 * Created by rachelleignacio on 3/7/17.
 * Executor responsible for running interactors on background threads.
 */
public interface Executor {
    void execute(final AbstractInteractor interactor);
}
