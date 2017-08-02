package com.rachelleignacio.listbucket.domain.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.domain.interactors.DeleteListItemInteractor;
import com.rachelleignacio.listbucket.domain.models.ListItem;

/**
 * Created by rachelleignacio on 3/10/17.
 */

public class DeleteListItemInteractorImpl extends AbstractInteractor implements DeleteListItemInteractor {
    private DeleteListItemInteractor.Callback callback;
    private DbInteractor database;
    private ListItem itemToDelete;
    private int listAdapterPosition;

    public DeleteListItemInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                        Callback callback, DbInteractor db, ListItem item, int position) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.itemToDelete = item;
        this.listAdapterPosition = position;
    }

    @Override
    public void run() {
        database.deleteListItem(itemToDelete);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListItemDeleted(listAdapterPosition);
            }
        });
    }
}
