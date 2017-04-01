package com.rachelleignacio.listbucket.presentation.presenters.impl;

import android.support.v4.app.FragmentManager;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.DeleteListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.DeleteListInteractorImpl;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.fragments.CreateListDialogFragment;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.MainActivityPresenter;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class MainActivityPresenterImpl extends AbstractPresenter implements MainActivityPresenter,
        CreateListInteractor.Callback, DeleteListInteractor.Callback {

    private DbInteractor dbInteractor;
    private MainActivityPresenter.View view;

    public MainActivityPresenterImpl(Executor executor, MainThread mainThread,
                                     DbInteractor database, View view) {
        super(executor, mainThread);
        this.dbInteractor = database;
        this.view = view;
    }

    @Override
    public void showCreateListDialog(FragmentManager fragmentManager) {
        CreateListDialogFragment.newInstance(this)
                .show(fragmentManager, CreateListDialogFragment.TAG);
    }

    @Override
    public void deleteListFromBucket(List listToDelete) {
        DeleteListInteractorImpl deleteListInteractor = new DeleteListInteractorImpl(executor,
                mainThread, this, dbInteractor, listToDelete);
        deleteListInteractor.execute();
    }

    @Override
    public void onListCreated() {
        view.showLists();
    }

    @Override
    public void onListDeleted() {
        view.showLists();
    }
}
