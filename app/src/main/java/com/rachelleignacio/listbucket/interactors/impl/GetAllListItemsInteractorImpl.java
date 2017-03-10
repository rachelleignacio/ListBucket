package com.rachelleignacio.listbucket.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.Executor;
import com.rachelleignacio.listbucket.executor.MainThread;
import com.rachelleignacio.listbucket.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.interactors.GetAllListItemsInteractor;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public class GetAllListItemsInteractorImpl extends AbstractInteractor implements GetAllListItemsInteractor {
    private GetAllListItemsInteractor.Callback callback;
    private DbInteractor database;
    private int parentListId;

    public GetAllListItemsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback, DbInteractor db, int parentListId) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.parentListId = parentListId;
    }

    @Override
    public void run() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListItemsRetrieved(database.getListItems(parentListId));
            }
        });
    }
}
