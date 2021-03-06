package com.rachelleignacio.listbucket.presentation.presenters.impl

import android.support.v4.app.FragmentManager
import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor
import com.rachelleignacio.listbucket.domain.interactors.DeleteListInteractor
import com.rachelleignacio.listbucket.domain.interactors.GetListBucketInteractor
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor
import com.rachelleignacio.listbucket.domain.interactors.impl.DeleteListInteractorImpl
import com.rachelleignacio.listbucket.domain.interactors.impl.GetListBucketInteractorImpl
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.fragments.CreateListDialogFragment
import com.rachelleignacio.listbucket.presentation.fragments.RenameListDialogFragment
import com.rachelleignacio.listbucket.presentation.fragments.newCreateListDialogFragmentInstance
import com.rachelleignacio.listbucket.presentation.fragments.newRenameListDialogFragmentInstance
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter

/**
 * Created by rachelleignacio on 8/23/17.
 */
class ListBucketFragmentPresenterImpl(threadExecutor: ThreadExecutor,
                                      mainThread: MainThread,
                                      private val dbInteractor: DbInteractor,
                                      private val view: ListBucketFragmentPresenter.View
) : AbstractPresenter(threadExecutor, mainThread),
        ListBucketFragmentPresenter,
        GetListBucketInteractor.Callback,
        CreateListInteractor.Callback,
        DeleteListInteractor.Callback,
        RenameListInteractor.Callback
{

    private var lists = mutableListOf<List>()

    override fun getLists() {
        GetListBucketInteractorImpl(threadExecutor, mainThread, this, dbInteractor)
            .execute()
    }

    override fun onListsRetrieved(lists: MutableList<List>) {
        this.lists.clear()
        this.lists.addAll(lists)
        view.showLists(lists)
    }

    override fun showCreateListDialog(fragmentManager: FragmentManager) {
        newCreateListDialogFragmentInstance(this)
                .show(fragmentManager, CreateListDialogFragment.TAG)
    }

    override fun onListCreated(newList: List) {
        lists.add(newList)
        view.showLists(lists)
        view.onClickList(newList)
    }

    override fun deleteListFromBucket(adapterPosition: Int) {
        DeleteListInteractorImpl(threadExecutor, mainThread, this, dbInteractor, lists[adapterPosition], adapterPosition)
            .execute()
    }

    override fun onListDeleted(adapterPosition: Int) {
        lists.removeAt(adapterPosition)
        view.showLists(lists)
    }

    override fun showRenameListDialog(fragmentManager: FragmentManager, listToRename: List) {
        newRenameListDialogFragmentInstance(this, listToRename)
                .show(fragmentManager, RenameListDialogFragment.TAG)
    }

    override fun onListRenamed() {
        view.showLists(lists)
    }
}