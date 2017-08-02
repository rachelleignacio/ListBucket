package com.rachelleignacio.listbucket.domain.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.DeleteListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/10/17.
 */

public class DeleteListInteractorImpl extends AbstractInteractor implements DeleteListInteractor {
    private DeleteListInteractor.Callback callback;
    private DbInteractor database;
    private List listToDelete;
    private int position;

    public DeleteListInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback,
                                    DbInteractor db, List listToDelete, int adapterPosition) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.database = db;
        this.listToDelete = listToDelete;
        this.position = adapterPosition;
    }

    @Override
    public void run() {
        database.deleteList(listToDelete);
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListDeleted(position);
            }
        });
    }
}
