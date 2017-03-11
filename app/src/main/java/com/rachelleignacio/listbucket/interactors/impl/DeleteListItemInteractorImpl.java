package com.rachelleignacio.listbucket.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.Executor;
import com.rachelleignacio.listbucket.executor.MainThread;
import com.rachelleignacio.listbucket.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.interactors.DeleteListItemInteractor;
import com.rachelleignacio.listbucket.models.ListItem;

/**
 * Created by rachelleignacio on 3/10/17.
 */

public class DeleteListItemInteractorImpl extends AbstractInteractor implements DeleteListItemInteractor {
    private DeleteListItemInteractor.Callback callback;
    private DbInteractor database;
    private ListItem itemToDelete;

    public DeleteListItemInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback, DbInteractor db, ListItem item) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.itemToDelete = item;
    }

    @Override
    public void run() {
        database.deleteListItem(itemToDelete);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListItemDeleted();
            }
        });
    }
}
