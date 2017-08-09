package com.rachelleignacio.listbucket.domain.interactors

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor
import com.rachelleignacio.listbucket.domain.models.ListItem

/**
 * Created by rachelleignacio on 8/9/17.
 */
interface GetAllListItemsInteractor : Interactor {
    interface Callback {
        fun onListItemsRetrieved(items: MutableList<ListItem>)
    }
}