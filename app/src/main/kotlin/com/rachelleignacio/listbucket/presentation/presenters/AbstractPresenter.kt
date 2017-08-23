package com.rachelleignacio.listbucket.presentation.presenters

import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor

/**
 * Created by rachelleignacio on 8/23/17.
 */
abstract class AbstractPresenter(protected val threadExecutor: ThreadExecutor,
                                 protected val mainThread: MainThread)