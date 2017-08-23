package com.rachelleignacio.listbucket.presentation.presenters.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.AddListItemInteractor
import com.rachelleignacio.listbucket.domain.interactors.DeleteListItemInteractor
import com.rachelleignacio.listbucket.domain.interactors.GetAllListItemsInteractor
import com.rachelleignacio.listbucket.domain.interactors.impl.AddListItemInteractorImpl
import com.rachelleignacio.listbucket.domain.interactors.impl.DeleteListItemInteractorImpl
import com.rachelleignacio.listbucket.domain.interactors.impl.GetAllListItemsInteractorImpl
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.domain.models.ListItem
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter
import com.rachelleignacio.listbucket.presentation.presenters.ListItemsFragmentPresenter

/**
 * Created by rachelleignacio on 8/23/17.
 */
class ListItemsFragmentPresenterImpl(threadExecutor: ThreadExecutor,
                                     mainThread: MainThread,
                                     private val dbInteractor: DbInteractor,
                                     private val view: ListItemsFragmentPresenter.View,
                                     private val parentList: List)
    : AbstractPresenter(threadExecutor, mainThread),
    ListItemsFragmentPresenter,
    GetAllListItemsInteractor.Callback,
    AddListItemInteractor.Callback,
    DeleteListItemInteractor.Callback {

    private var listItems: MutableList<ListItem>? = null

    override fun getListItems() {
        val getItemsInteractor = GetAllListItemsInteractorImpl(threadExecutor, mainThread,
                this, dbInteractor, parentList.id!!)
        getItemsInteractor.execute()
    }

    override fun onListItemsRetrieved(items: MutableList<ListItem>) {
        listItems = ArrayList()
        listItems!!.addAll(items)
        view.showListItems(listItems!!)
    }

    override fun addListItem(listItemName: String) {
        val itemToSave = ListItem(parentList, listItemName)
        val addItemInteractor = AddListItemInteractorImpl(threadExecutor, mainThread, this,
                dbInteractor, itemToSave)
        addItemInteractor.execute()
    }

    override fun onListItemAdded(newItem: ListItem) {
        listItems!!.add(newItem)
        view.showListItems(listItems!!)
    }

    override fun deleteListItem(adapterPosition: Int) {
        val deleteItemInteractor = DeleteListItemInteractorImpl(threadExecutor, mainThread, this,
                dbInteractor, listItems!!.get(adapterPosition), adapterPosition)
        deleteItemInteractor.execute()
    }

    override fun onListItemDeleted(adapterPosition: Int) {
        listItems!!.removeAt(adapterPosition)
        view.showListItems(listItems!!)
    }

    override val listCount: Int
        get() = listItems?.size ?: 0
}