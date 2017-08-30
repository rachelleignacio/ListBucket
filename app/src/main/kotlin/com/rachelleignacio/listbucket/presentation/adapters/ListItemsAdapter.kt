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


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.list_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val listItem = listItems[position]
        holder!!.listItemNameTv.text = listItem.name
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onRowDismiss(position: Int) {
        view.onItemSwipedToDelete(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listItemNameTv: TextView = itemView.findViewById(R.id.item_name)
    }
}