package com.rachelleignacio.listbucket.domain.executor.impl

import android.os.Handler
import android.os.Looper
import com.rachelleignacio.listbucket.domain.executor.MainThread

/**
 * Created by rachelleignacio on 8/4/17.
 * This makes sure that the provided runnable will be run on the main UI thread.
 */
object MainThreadImpl : MainThread {
    private var handler: Handler = Handler(Looper.getMainLooper())

    override fun post(runnable: Runnable) { handler.post(runnable) }
}