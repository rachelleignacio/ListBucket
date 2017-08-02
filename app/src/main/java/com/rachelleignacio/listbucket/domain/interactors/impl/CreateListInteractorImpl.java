package com.rachelleignacio.listbucket.domain.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/7/17.
 */

public class CreateListInteractorImpl extends AbstractInteractor implements CreateListInteractor {
    private CreateListInteractor.Callback callback;
    private DbInteractor database;
    private List newList;

    public CreateListInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    Callback callback, DbInteractor db, List newList) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.newList = newList;
    }

    @Override
    public void run() {
        database.saveList(newList);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListCreated(newList);
            }
        });
    }
}
