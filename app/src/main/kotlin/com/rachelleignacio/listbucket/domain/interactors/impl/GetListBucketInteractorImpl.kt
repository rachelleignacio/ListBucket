package com.rachelleignacio.listbucket.domain.interactors.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.GetListBucketInteractor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor

/**
 * Created by rachelleignacio on 8/9/17.
 */
class GetListBucketInteractorImpl(threadExecutor: ThreadExecutor,
                                  mainThread: MainThread,
                                  private val callback: GetListBucketInteractor.Callback,
                                  private val dbInteractor: DbInteractor)
    : AbstractInteractor(threadExecutor, mainThread), GetListBucketInteractor {

    override fun run() {
        mainThread.post(Runnable { callback.onListsRetrieved(dbInteractor.getAllLists()) })
    }
}