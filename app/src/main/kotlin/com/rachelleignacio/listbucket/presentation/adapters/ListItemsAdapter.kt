package com.rachelleignacio.listbucket.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.domain.models.ListItem
import com.rachelleignacio.listbucket.presentation.listeners.ListAdapterTouchListener
import com.rachelleignacio.listbucket.presentation.presenters.ListItemsFragmentPresenter

/**
 * Created by rachelleignacio on 8/29/17.
 */
class ListItemsAdapter(private val listItems: MutableList<ListItem>,
                       private val view: ListItemsFragmentPresenter.View)
    : RecyclerView.Adapter<ListItemsAdapter.ViewHolder>(), ListAdapterTouchListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_item_layout, parent, false)
                    .run { ViewHolder(this) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.listItemNameTv.text = listItems[position].name
    }

    override fun getItemCount() = listItems.size

    override fun onRowDismiss(position: Int) { view.onItemSwipedToDelete(position) }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listItemNameTv: TextView = itemView.findViewById(R.id.item_name)
    }
}