package com.rachelleignacio.listbucket.domain.interactors

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor
import com.rachelleignacio.listbucket.domain.models.List

/**
 * Created by rachelleignacio on 8/9/17.
 */
interface GetListBucketInteractor : Interactor {
    interface Callback {
        fun onListsRetrieved(lists: MutableList<List>)
    }
}