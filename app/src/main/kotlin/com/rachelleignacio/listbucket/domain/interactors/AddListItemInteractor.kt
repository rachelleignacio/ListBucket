package com.rachelleignacio.listbucket.domain.interactors

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor
import com.rachelleignacio.listbucket.domain.models.ListItem

/**
 * Created by rachelleignacio on 8/9/17.
 */
interface AddListItemInteractor : Interactor {
    interface Callback {
        fun onListItemAdded(newItem: ListItem)
    }
}