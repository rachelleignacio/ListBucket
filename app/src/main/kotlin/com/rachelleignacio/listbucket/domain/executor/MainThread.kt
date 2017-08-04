package com.rachelleignacio.listbucket.domain.executor

/**
 * Created by rachelleignacio on 3/6/17.
 * Interface that defines a class that enables interactors to run certain operations on the main
 * (UI) thread.
 */
interface MainThread {
    fun post(runnable: Runnable)
}