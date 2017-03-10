package com.rachelleignacio.listbucket.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.Executor;
import com.rachelleignacio.listbucket.executor.MainThread;
import com.rachelleignacio.listbucket.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.interactors.AddListItemInteractor;
import com.rachelleignacio.listbucket.models.List;
import com.rachelleignacio.listbucket.models.ListItem;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public class AddListItemInteractorImpl extends AbstractInteractor implements AddListItemInteractor {
    private AddListItemInteractor.Callback callback;
    private DbInteractor database;
    private List parentList;
    private String itemName;

    public AddListItemInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     Callback callback, DbInteractor db, List parent, String name) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.parentList = parent;
        this.itemName = name;
    }

    @Override
    public void run() {
        ListItem itemToSave = new ListItem(parentList, itemName);
        database.saveListItem(itemToSave);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListItemAdded();
            }
        });
    }
}
