package com.rachelleignacio.listbucket.domain.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.domain.interactors.GetListBucketInteractor;

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
