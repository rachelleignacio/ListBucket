package com.rachelleignacio.listbucket.domain.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor;
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor;
import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 4/18/17.
 */

public class RenameListInteractorImpl extends AbstractInteractor implements RenameListInteractor {
    private RenameListInteractor.Callback callback;
    private DbInteractor database;
    private List listToRename;
    private String newListName;

    public RenameListInteractorImpl(ThreadExecutor threadExecutor, MainThread mainThread, Callback callback,
                                    DbInteractor db, List listToRename, String newName) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.listToRename = listToRename;
        this.newListName = newName;
    }

    @Override
    public void run() {
        database.renameList(listToRename, newListName);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListRenamed();
            }
        });
    }
}
