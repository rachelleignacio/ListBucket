package com.rachelleignacio.listbucket.presentation.presenters

import com.rachelleignacio.listbucket.domain.models.List

/**
 * Created by rachelleignacio on 8/23/17.
 */
interface RenameListFragmentPresenter {
    fun renameList(listToRename: List, newName: String)
}