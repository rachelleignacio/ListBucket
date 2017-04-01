package com.rachelleignacio.listbucket.presentation.presenters.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.GetListBucketInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.GetListBucketInteractorImpl;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class ListBucketFragmentPresenterImpl extends AbstractPresenter implements
        ListBucketFragmentPresenter, GetListBucketInteractor.Callback {

    private DbInteractor dbInteractor;
    private ListBucketFragmentPresenter.View view;

    public ListBucketFragmentPresenterImpl(Executor executor, MainThread mainThread,
                                           DbInteractor db, View view) {
        super(executor, mainThread);
        this.dbInteractor = db;
        this.view = view;
    }

    @Override
    public void getLists() {
        GetListBucketInteractor getListsInterator = new GetListBucketInteractorImpl(executor,
                mainThread, this, dbInteractor);
        getListsInterator.execute();
    }

    @Override
    public void onListsRetrieved(java.util.List<List> lists) {
        view.showLists(lists);
    }
}
