package com.rachelleignacio.listbucket.presentation.presenters.impl;

import android.support.v4.app.FragmentManager;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.DeleteListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.GetListBucketInteractor;
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.DeleteListInteractorImpl;
import com.rachelleignacio.listbucket.domain.interactors.impl.GetListBucketInteractorImpl;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.fragments.CreateListDialogFragment;
import com.rachelleignacio.listbucket.presentation.fragments.RenameListDialogFragment;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter;

import java.util.ArrayList;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class ListBucketFragmentPresenterImpl extends AbstractPresenter implements
        ListBucketFragmentPresenter, GetListBucketInteractor.Callback, CreateListInteractor.Callback,
        DeleteListInteractor.Callback, RenameListInteractor.Callback {

    private DbInteractor dbInteractor;
    private ListBucketFragmentPresenter.View view;
    private java.util.List<List> lists;

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
        this.lists = new ArrayList<>();
        this.lists.addAll(lists);
        view.showLists(lists);
    }

    @Override
    public void showCreateListDialog(FragmentManager fragmentManager) {
        CreateListDialogFragment.newInstance(this)
                .show(fragmentManager, CreateListDialogFragment.TAG);
    }

    @Override
    public void onListCreated(List newList) {
        lists.add(newList);
        view.showLists(lists);
        view.onCLickList(newList);
    }

    @Override
    public void deleteListFromBucket(int position) {
        DeleteListInteractorImpl deleteListInteractor = new DeleteListInteractorImpl(executor,
                mainThread, this, dbInteractor, lists.get(position), position);
        deleteListInteractor.execute();
    }

    @Override
    public void onListDeleted(int position) {
        lists.remove(position);
        view.showLists(lists);
    }

    @Override
    public void showRenameListDialog(FragmentManager fragmentManager, List listToRename) {
        RenameListDialogFragment.newInstance(this, listToRename)
                .show(fragmentManager, RenameListDialogFragment.TAG);
    }

    @Override
    public void onListRenamed() {
        view.showLists(lists);
    }
}
