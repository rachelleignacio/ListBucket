package com.rachelleignacio.listbucket.domain.interactors.base

/**
 * Created by rachelleignacio on 8/9/17.
 * Interface containing the main method that starts an interactor, making sure the operation is done
 * on a background thread.
 */
interface Interactor {
    fun execute()
}