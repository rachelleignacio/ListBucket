package com.rachelleignacio.listbucket.domain.interactors.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor
import com.rachelleignacio.listbucket.domain.models.List

/**
 * Created by rachelleignacio on 8/9/17.
 */
class CreateListInteractorImpl(threadExecutor: ThreadExecutor,
                               mainThread: MainThread,
                               private val callback: CreateListInteractor.Callback,
                               private val dbInteractor: DbInteractor,
                               private val newList: List
) : AbstractInteractor(threadExecutor, mainThread), CreateListInteractor {

    override fun run() {
        dbInteractor.saveList(newList)
        mainThread.post(Runnable { callback.onListCreated(newList) })
    }
}