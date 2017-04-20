package com.rachelleignacio.listbucket.presentation.presenters.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.RenameListInteractorImpl;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.RenameListFragmentPresenter;

/**
 * Created by rachelleignacio on 4/19/17.
 */

public class RenameListFragmentPresenterImpl extends AbstractPresenter
        implements RenameListFragmentPresenter {

    private RenameListInteractor.Callback callback;
    private DbInteractor dbInteractor;

    public RenameListFragmentPresenterImpl(Executor executor, MainThread mainThread,
                                           RenameListInteractor.Callback callback, DbInteractor db) {
        super(executor, mainThread);
        this.callback = callback;
        this.dbInteractor = db;
    }

    @Override
    public void renameList(List listToRename, String newListName) {
        RenameListInteractor renameListInteractor = new RenameListInteractorImpl(executor, mainThread,
                callback, dbInteractor, listToRename, newListName);
        renameListInteractor.execute();
    }
}
