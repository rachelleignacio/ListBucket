package com.rachelleignacio.listbucket.presentation.listeners

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by rachelleignacio on 8/23/17.
 */
class ListTouchListenerCallback(private val listTouchListener: ListAdapterTouchListener) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = false

    override fun isItemViewSwipeEnabled() = true

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) =
        makeMovementFlags(0, ItemTouchHelper.START or ItemTouchHelper.END)

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) =
        (viewHolder!!.itemViewType == target!!.itemViewType)

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        listTouchListener.onRowDismiss(viewHolder!!.adapterPosition)
    }
}