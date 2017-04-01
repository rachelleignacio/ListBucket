package com.rachelleignacio.listbucket.presentation.presenters.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.domain.interactors.GetListBucketInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.GetListBucketInteractorImpl;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.MainListBucketFragmentPresenter;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class MainListBucketFragmentPresenterImpl extends AbstractPresenter implements
        MainListBucketFragmentPresenter, GetListBucketInteractor.Callback {

    private DbInteractor dbInteractor;
    private MainListBucketFragmentPresenter.View view;

    public MainListBucketFragmentPresenterImpl(Executor executor, MainThread mainThread,
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
        view.onListsRetrieved(lists);
    }
}
