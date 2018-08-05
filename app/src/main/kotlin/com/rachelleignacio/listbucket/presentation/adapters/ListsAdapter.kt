package com.rachelleignacio.listbucket.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.listeners.ListAdapterTouchListener
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter

/**
 * Created by rachelleignacio on 8/29/17.
 */
class ListsAdapter(private val lists: MutableList<List>, private val view: ListBucketFragmentPresenter.View)
        : RecyclerView.Adapter<ListsAdapter.ViewHolder>(), ListAdapterTouchListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_bucket_item_layout, parent, false)
                .run { ViewHolder(this) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = lists[position]
        holder.listNameTv.text = list.name
        holder.renameListIv.setOnClickListener { view.onClickRenameList(list) }
        holder.itemView.setOnClickListener { view.onClickList(list) }
    }

    override fun getItemCount() = lists.size


    override fun onRowDismiss(position: Int) { view.onListSwipedToDelete(position) }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var listNameTv: TextView = itemView.findViewById(R.id.list_name)
        var renameListIv: ImageView = itemView.findViewById(R.id.rename_list)
    }
}
