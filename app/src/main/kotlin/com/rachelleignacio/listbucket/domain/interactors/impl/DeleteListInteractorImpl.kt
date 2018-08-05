package com.rachelleignacio.listbucket.domain.interactors.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.DeleteListInteractor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor
import com.rachelleignacio.listbucket.domain.models.List

/**
 * Created by rachelleignacio on 8/9/17.
 */
class DeleteListInteractorImpl(threadExecutor: ThreadExecutor,
                               mainThread: MainThread,
                               private val callback: DeleteListInteractor.Callback,
                               private val dbInteractor: DbInteractor,
                               private val listToDelete: List,
                               private val adapterPosition: Int
) : AbstractInteractor(threadExecutor, mainThread), DeleteListInteractor {

    override fun run() {
        dbInteractor.deleteList(listToDelete)
        mainThread.post(Runnable { callback.onListDeleted(adapterPosition) })
    }
}