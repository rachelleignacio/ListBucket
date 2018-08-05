package com.rachelleignacio.listbucket.domain.interactors.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.GetAllListItemsInteractor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor

/**
 * Created by rachelleignacio on 8/9/17.
 */
class GetAllListItemsInteractorImpl(threadExecutor: ThreadExecutor,
                                    mainThread: MainThread,
                                    private val callback: GetAllListItemsInteractor.Callback,
                                    private val dbInteractor: DbInteractor,
                                    private val parentListId: Int
) : AbstractInteractor(threadExecutor, mainThread), GetAllListItemsInteractor {

    override fun run() {
        mainThread.post(Runnable { callback.onListItemsRetrieved(dbInteractor.getListItems(parentListId)) })
    }
}