package com.rachelleignacio.listbucket.domain.interactors.impl

import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor
import com.rachelleignacio.listbucket.domain.models.List

/**
 * Created by rachelleignacio on 8/9/17.
 */
class RenameListInteractorImpl(threadExecutor: ThreadExecutor,
                               mainThread: MainThread,
                               private val callback: RenameListInteractor.Callback,
                               private val dbInteractor: DbInteractor,
                               private val listToRename: List,
                               private val newName: String)
    : AbstractInteractor(threadExecutor, mainThread), RenameListInteractor {

    override fun run() {
        dbInteractor.renameList(listToRename, newName)
        mainThread.post(Runnable { callback.onListRenamed() })
    }
}