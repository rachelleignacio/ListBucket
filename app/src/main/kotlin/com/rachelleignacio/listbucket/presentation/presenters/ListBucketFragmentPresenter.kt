package com.rachelleignacio.listbucket.presentation.presenters

import android.support.v4.app.FragmentManager
import com.rachelleignacio.listbucket.domain.models.List

/**
 * Created by rachelleignacio on 8/23/17.
 */
interface ListBucketFragmentPresenter {
    interface View {
        fun showLists(lists: MutableList<List>)
        fun onClickList(listToView: List)
        fun onClickCreateList()
        fun onListSwipedToDelete(adapterPosition: Int)
        fun onClickRenameList(listToRename: List)
    }

    fun getLists()
    fun showCreateListDialog(fragmentManager: FragmentManager)
    fun deleteListFromBucket(adapterPosition: Int)
    fun showRenameListDialog(fragmentManager: FragmentManager, listToRename: List)
}