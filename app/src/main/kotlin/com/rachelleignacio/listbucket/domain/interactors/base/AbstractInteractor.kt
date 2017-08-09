package com.rachelleignacio.listbucket.domain.interactors.base

import com.rachelleignacio.listbucket.domain.executor.MainThread
import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor

/**
 * Created by rachelleignacio on 8/9/17.
 */
abstract class AbstractInteractor(protected var threadExecutor: ThreadExecutor, protected var mainThread: MainThread) : Interactor {

    @Volatile protected var isCanceled: Boolean? = null
    @Volatile protected var isRunning: Boolean? = null

    /**
     * This method contains the actual business logic of the interactor. It SHOULD NOT BE USED
     * DIRECTLY, but instead the execute() method of the interactor should be called to ensure the
     * operation is done on a background thread.
     */
    abstract fun run()

    override fun execute() {
        isRunning = true
        threadExecutor!!.execute(this)
    }

    fun cancel() {
        isCanceled = true
        isRunning = false
    }

    fun onFinished() {
        isRunning = false
        isCanceled = false
    }
}