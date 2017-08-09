package com.rachelleignacio.listbucket.domain.interactors.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.AddListItemInteractor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor
import com.rachelleignacio.listbucket.domain.models.ListItem

/**
 * Created by rachelleignacio on 8/9/17.
 */
class AddListItemInteractorImpl(threadExecutor: ThreadExecutor,
                                mainThread: MainThread,
                                private val callback: AddListItemInteractor.Callback,
                                private val dbInteractor: DbInteractor,
                                private val newItem: ListItem) : AbstractInteractor(threadExecutor, mainThread), AddListItemInteractor {
    override fun run() {
        dbInteractor.saveListItem(newItem)
        mainThread.post(Runnable { callback.onListItemAdded(newItem) })
    }
}