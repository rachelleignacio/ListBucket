package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.activities.MainActivity
import com.rachelleignacio.listbucket.presentation.adapters.ListsAdapter
import com.rachelleignacio.listbucket.presentation.listeners.ListTouchListenerCallback
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter
import com.rachelleignacio.listbucket.presentation.presenters.impl.ListBucketFragmentPresenterImpl

/**
 * Created by rachelleignacio on 8/30/17.
 */
class MainListBucketFragment @SuppressLint("ValidFragment") internal constructor() : Fragment(),
        ListBucketFragmentPresenter.View {

    private lateinit var presenter: ListBucketFragmentPresenter
    private lateinit var listsAdapter: ListsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_list_bucket_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ListBucketFragmentPresenterImpl(ThreadExecutorImpl.instance, MainThreadImpl.instance,
                DbInteractor.instance, this)

        showFabAddButton()
        initLists()
    }

    private fun showFabAddButton() {
        val fab = view!!.findViewById<FloatingActionButton>(R.id.fab)
        if (fab != null) {
            fab.visibility = View.VISIBLE
            fab.setOnClickListener { onClickCreateList() }
        }
    }

    private fun initLists() {
        presenter.getLists()
    }

    override fun showLists(lists: MutableList<List>) {
        if (activity != null) {
            val listsRecyclerView = activity!!.findViewById<RecyclerView>(R.id.recycler_view_items)
            listsRecyclerView.setHasFixedSize(true)
            listsRecyclerView.layoutManager = LinearLayoutManager(activity)
            listsRecyclerView.adapter = null

            listsAdapter = ListsAdapter(lists, this)
            listsRecyclerView.adapter = listsAdapter

            val listTouchCallback = ListTouchListenerCallback(listsAdapter)
            val listTouchListener = ItemTouchHelper(listTouchCallback)
            listTouchListener.attachToRecyclerView(listsRecyclerView)
        }
    }

    override fun onClickList(listToView: List) {
        (activity as MainActivity).showList(listToView)
    }

    override fun onClickCreateList() {
        presenter.showCreateListDialog(activity.supportFragmentManager)
    }

    override fun onListSwipedToDelete(adapterPosition: Int) {
        presenter.deleteListFromBucket(adapterPosition)
        listsAdapter.notifyItemRemoved(adapterPosition)
    }

    override fun onClickRenameList(listToRename: List) {
        presenter.showRenameListDialog(activity!!.supportFragmentManager, listToRename)
    }

    companion object {
        fun newInstance(): MainListBucketFragment {
            return MainListBucketFragment()
        }
    }
}