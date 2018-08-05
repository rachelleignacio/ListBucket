package com.rachelleignacio.listbucket.domain.interactors.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.DeleteListItemInteractor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor
import com.rachelleignacio.listbucket.domain.models.ListItem

/**
 * Created by rachelleignacio on 8/9/17.
 */
class DeleteListItemInteractorImpl(threadExecutor: ThreadExecutor,
                                   mainThread: MainThread,
                                   private val callback: DeleteListItemInteractor.Callback,
                                   private val dbInteractor: DbInteractor,
                                   private val itemToDelete: ListItem,
                                   private val adapterPosition: Int
) : AbstractInteractor(threadExecutor, mainThread), DeleteListItemInteractor {

    override fun run() {
        dbInteractor.deleteListItem(itemToDelete)
        mainThread.post(Runnable { callback.onListItemDeleted(adapterPosition) })
    }
}