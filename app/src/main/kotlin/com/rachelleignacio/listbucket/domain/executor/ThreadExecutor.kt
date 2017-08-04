package com.rachelleignacio.listbucket.domain.executor

import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor

/**
 * Created by rachelleignacio on 8/4/17.
 * ThreadExecutor responsible for running interactors on background threads.
 */
interface ThreadExecutor {
    fun execute(interactor: AbstractInteractor)
}