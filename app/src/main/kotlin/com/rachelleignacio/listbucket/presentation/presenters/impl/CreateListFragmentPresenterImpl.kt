package com.rachelleignacio.listbucket.presentation.presenters.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor
import com.rachelleignacio.listbucket.domain.interactors.impl.CreateListInteractorImpl
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter
import com.rachelleignacio.listbucket.presentation.presenters.CreateListFragmentPresenter

/**
 * Created by rachelleignacio on 8/23/17.
 */
class CreateListFragmentPresenterImpl(threadExecutor: ThreadExecutor,
                                      mainThread: MainThread,
                                      private val callback: CreateListInteractor.Callback,
                                      private val dbInteractor: DbInteractor
) : AbstractPresenter(threadExecutor, mainThread), CreateListFragmentPresenter {

    override fun createList(listName: String) {
        CreateListInteractorImpl(threadExecutor, mainThread, callback, dbInteractor, List(listName))
                .execute()
    }
}