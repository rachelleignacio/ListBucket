package com.rachelleignacio.listbucket.presentation.presenters.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.CreateListInteractorImpl;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.CreateListFragmentPresenter;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class CreateListFragmentPresenterImpl extends AbstractPresenter implements CreateListFragmentPresenter {
    private CreateListInteractor.Callback callback;
    private DbInteractor dbInteractor;

    public CreateListFragmentPresenterImpl(Executor executor, MainThread mainThread,
                                           CreateListInteractor.Callback callback, DbInteractor db) {
        super(executor, mainThread);
        this.callback = callback;
        this.dbInteractor = db;
    }

    @Override
    public void createList(String listName) {
        List listToSave = new List(listName);
        CreateListInteractor createListInteractor = new CreateListInteractorImpl(executor,
                mainThread, callback, dbInteractor, listToSave);
        createListInteractor.execute();
    }
}
