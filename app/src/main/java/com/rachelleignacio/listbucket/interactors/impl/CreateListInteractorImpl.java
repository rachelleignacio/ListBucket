package com.rachelleignacio.listbucket.interactors.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.Executor;
import com.rachelleignacio.listbucket.executor.MainThread;
import com.rachelleignacio.listbucket.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.interactors.base.AbstractInteractor;
import com.rachelleignacio.listbucket.models.List;

/**
 * Created by rachelleignacio on 3/7/17.
 */

public class CreateListInteractorImpl extends AbstractInteractor implements CreateListInteractor {
    private CreateListInteractor.Callback callback;
    private String listName;

    public CreateListInteractorImpl(Executor threadExecutor, MainThread mainThread,
            Callback callback, String name) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.listName = name;
    }

    @Override
    public void run() {
        DbInteractor.getInstance().saveList(new List(listName));
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onListCreated();
            }
        });
    }
}
