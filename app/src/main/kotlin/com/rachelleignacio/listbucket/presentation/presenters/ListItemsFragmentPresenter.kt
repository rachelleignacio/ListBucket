package com.rachelleignacio.listbucket.presentation.presenters

import com.rachelleignacio.listbucket.domain.models.ListItem

/**
 * Created by rachelleignacio on 8/23/17.
 */
interface ListItemsFragmentPresenter {
    interface View {
        fun showListItems(items: MutableList<ListItem>)
        fun onItemSwipedToDelete(adapterPosition: Int)
    }

    fun getListItems()
    val listCount: Int
    fun addListItem(listItemName: String)
    fun deleteListItem(adapterPosition: Int)
}