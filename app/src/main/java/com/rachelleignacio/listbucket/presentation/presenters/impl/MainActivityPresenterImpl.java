package com.rachelleignacio.listbucket.presentation.presenters.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.MainActivityPresenter;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class MainActivityPresenterImpl extends AbstractPresenter implements MainActivityPresenter {

    private DbInteractor dbInteractor;
    private MainActivityPresenter.View view;

    public MainActivityPresenterImpl(ThreadExecutor threadExecutor, MainThread mainThread,
                                     DbInteractor database, View view) {
        super(threadExecutor, mainThread);
        this.dbInteractor = database;
        this.view = view;
    }
}
