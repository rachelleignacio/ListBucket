package com.rachelleignacio.listbucket.domain.interactors

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor
import com.rachelleignacio.listbucket.domain.models.List

/**
 * Created by rachelleignacio on 8/9/17.
 */
interface CreateListInteractor : Interactor {
    interface Callback {
        fun onListCreated(newList: List)
    }
}