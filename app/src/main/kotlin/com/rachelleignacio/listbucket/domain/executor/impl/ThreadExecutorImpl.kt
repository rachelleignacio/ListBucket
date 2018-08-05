package com.rachelleignacio.listbucket.domain.executor.impl

import com.rachelleignacio.listbucket.domain.executor.ThreadExecutor
import com.rachelleignacio.listbucket.domain.interactors.base.AbstractInteractor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

private const val CORE_POOL_SIZE = 3
private const val MAX_POOL_SIZE = 5
private const val KEEP_ALIVE_TIME = 120
private val TIME_UNIT = TimeUnit.SECONDS
private val WORK_QUEUE = LinkedBlockingQueue<Runnable>()

/**
 * Created by rachelleignacio on 8/4/17.
 */
object ThreadExecutorImpl : ThreadExecutor {

    private val threadPoolExecutor = ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME.toLong(), TIME_UNIT, WORK_QUEUE)

    override fun execute(interactor: AbstractInteractor) {
        threadPoolExecutor.submit {
            interactor.run()
            interactor.onFinished()
        }
    }
}