package com.rachelleignacio.listbucket.presentation.presenters.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor
import com.rachelleignacio.listbucket.domain.interactors.impl.RenameListInteractorImpl
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter
import com.rachelleignacio.listbucket.presentation.presenters.RenameListFragmentPresenter

/**
 * Created by rachelleignacio on 8/23/17.
 */
class RenameListFragmentPresenterImpl(threadExecutor: ThreadExecutor,
                                      mainThread: MainThread,
                                      private val callback: RenameListInteractor.Callback,
                                      private val dbInteractor: DbInteractor)
    : AbstractPresenter(threadExecutor, mainThread), RenameListFragmentPresenter {

    override fun renameList(listToRename: List, newName: String) {
        RenameListInteractorImpl(threadExecutor, mainThread, callback, dbInteractor, listToRename, newName)
                .execute()
    }
}