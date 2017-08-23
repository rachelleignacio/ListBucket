package com.rachelleignacio.listbucket.presentation.presenters.impl

import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.presentation.presenters.AbstractPresenter
import com.rachelleignacio.listbucket.presentation.presenters.MainActivityPresenter

/**
 * Created by rachelleignacio on 8/23/17.
 */
class MainActivityPresenterImpl(threadExecutor: ThreadExecutor, mainThread: MainThread)
    : AbstractPresenter(threadExecutor, mainThread), MainActivityPresenter