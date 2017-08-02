package com.rachelleignacio.listbucket.presentation.presenters.impl;

import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.Executor;
import com.rachelleignacio.listbucket.domain.executor.MainThread;
import com.rachelleignacio.listbucket.domain.interactors.AddListItemInteractor;
import com.rachelleignacio.listbucket.domain.interactors.DeleteListItemInteractor;
import com.rachelleignacio.listbucket.domain.interactors.GetAllListItemsInteractor;
import com.rachelleignacio.listbucket.domain.interactors.impl.AddListItemInteractorImpl;
import com.rachelleignacio.listbucket.domain.interactors.impl.DeleteListItemInteractorImpl;
import com.rachelleignacio.listbucket.domain.interactors.impl.GetAllListItemsInteractorImpl;
import com.rachelleignacio.listbucket.domain.models.ListItem;
import com.rachelleignacio.listbucket.presentation.adapters.ListItemsAdapter;
import com.rachelleignacio.listbucket.presentation.fragments.ListItemsFragment;
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.ListItemsFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public class ListItemsFragmentPresenterImpl extends AbstractPresenter implements
        ListItemsFragmentPresenter, GetAllListItemsInteractor.Callback,
        AddListItemInteractor.Callback, DeleteListItemInteractor.Callback {

    private DbInteractor dbInteractor;
    private ListItemsFragmentPresenter.View view;
    private com.rachelleignacio.listbucket.domain.models.List parentList;
    private java.util.List<ListItem> listItems;

    public ListItemsFragmentPresenterImpl(Executor executor, MainThread mainThread, DbInteractor db,
                                          View view, com.rachelleignacio.listbucket.domain.models.List parent) {
        super(executor, mainThread);
        this.dbInteractor = db;
        this.view = view;
        this.parentList = parent;
    }

    @Override
    public void getListItems() {
        GetAllListItemsInteractor getItemsInteractor = new GetAllListItemsInteractorImpl(executor,
                mainThread, this, dbInteractor, parentList.getId());
        getItemsInteractor.execute();
    }

    @Override
    public void onListItemsRetrieved(List<ListItem> items) {
        listItems = new ArrayList<>();
        listItems.addAll(items);
        view.showListItems(listItems);
    }

    @Override
    public void addListItem(String listItemName) {
        ListItem itemToSave = new ListItem(parentList, listItemName);
        AddListItemInteractor addItemInteractor = new AddListItemInteractorImpl(executor, mainThread,
                this, dbInteractor, parentList, itemToSave);
        addItemInteractor.execute();
    }

    @Override
    public void onListItemAdded(ListItem newItem) {
        listItems.add(newItem);
        view.showListItems(listItems);
    }

    @Override
    public void deleteListItem(int position) {
        DeleteListItemInteractor deleteListItemInteractor = new DeleteListItemInteractorImpl(executor,
                mainThread, this, dbInteractor, listItems.get(position), position);
        deleteListItemInteractor.execute();
    }

    @Override
    public void onListItemDeleted(int position) {
        listItems.remove(position);
        view.showListItems(listItems);
    }

    @Override
    public int getListCount() {
        return listItems.size();
    }
}
