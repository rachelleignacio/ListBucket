package com.rachelleignacio.listbucket.domain.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.domain.interactors.AddListItemInteractor;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.domain.models.ListItem;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public class AddListItemInteractorImpl extends AbstractInteractor implements AddListItemInteractor {
    private AddListItemInteractor.Callback callback;
    private DbInteractor database;
    private List parentList;
    private ListItem newItem;

    public AddListItemInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     Callback callback, DbInteractor db, List parent, ListItem newItem) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.parentList = parent;
        this.newItem = newItem;
    }

    @Override
    public void run() {
        database.saveListItem(newItem);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListItemAdded(newItem);
            }
        });
    }
}
