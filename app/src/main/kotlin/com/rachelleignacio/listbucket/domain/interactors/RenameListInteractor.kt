package com.rachelleignacio.listbucket.domain.interactors

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor

/**
 * Created by rachelleignacio on 8/9/17.
 */
interface RenameListInteractor : Interactor {
    interface Callback {
        fun onListRenamed()
    }
}