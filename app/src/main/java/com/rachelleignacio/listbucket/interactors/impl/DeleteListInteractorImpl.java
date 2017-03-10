package com.rachelleignacio.listbucket.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.Executor;
import com.rachelleignacio.listbucket.executor.MainThread;
import com.rachelleignacio.listbucket.interactors.DeleteListInteractor;
import com.rachelleignacio.listbucket.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.models.List;

/**
 * Created by rachelleignacio on 3/10/17.
 */

public class DeleteListInteractorImpl extends AbstractInteractor implements DeleteListInteractor {
    private DeleteListInteractor.Callback callback;
    private DbInteractor database;
    private List listToDelete;

    public DeleteListInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback,
                                    DbInteractor db, List listToDelete) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.listToDelete = listToDelete;
    }

    @Override
    public void run() {
        database.deleteList(listToDelete);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListDeleted();
            }
        });
    }
}
