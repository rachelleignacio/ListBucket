package com.rachelleignacio.listbucket.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.Executor;
import com.rachelleignacio.listbucket.executor.MainThread;
import com.rachelleignacio.listbucket.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.interactors.GetListBucketInteractor;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public class GetListBucketInteractorImpl extends AbstractInteractor implements GetListBucketInteractor {
    private GetListBucketInteractor.Callback callback;
    private DbInteractor database;

    public GetListBucketInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                       Callback callback, DbInteractor db) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
    }

    @Override
    public void run() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListsRetrieved(database.getAllLists());
            }
        });
    }
}
