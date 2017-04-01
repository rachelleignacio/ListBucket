package com.rachelleignacio.listbucket.presentation.presenters.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.CreateListInteractorImpl;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.CreateListFragmentPresenter;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class CreateListFragmentPresenterImpl extends AbstractPresenter implements CreateListFragmentPresenter {
    private CreateListInteractor.Callback callback;
    private DbInteractor database;

    public CreateListFragmentPresenterImpl(Executor executor, MainThread mainThread,
                                           CreateListInteractor.Callback callback, DbInteractor db) {
        super(executor, mainThread);
        this.callback = callback;
        this.database = db;
    }

    @Override
    public void createList(String listName) {
        CreateListInteractor createListInteractor = new CreateListInteractorImpl(executor,
                mainThread, callback, database, listName);
        createListInteractor.execute();
    }
}
