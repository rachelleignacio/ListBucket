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

fun newMainListBucketInstance() = MainListBucketFragment()

/**
 * Created by rachelleignacio on 8/30/17.
 */
class MainListBucketFragment @SuppressLint("ValidFragment") internal constructor() : Fragment(), ListBucketFragmentPresenter.View {

    private lateinit var presenter: ListBucketFragmentPresenter
    private lateinit var listsAdapter: ListsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list_bucket_layout, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListBucketFragmentPresenterImpl(ThreadExecutorImpl, MainThreadImpl, DbInteractor, this)
        showFabAddButton()
        initLists()
    }

    private fun showFabAddButton() { view?.findViewById<FloatingActionButton>(R.id.fab)?.init() }

    private fun FloatingActionButton.init() {
        visibility = View.VISIBLE
        setOnClickListener { onClickCreateList() }
    }

    private fun initLists() { presenter.getLists() }

    override fun showLists(lists: MutableList<List>) {
        if (activity != null) {
            listsAdapter = ListsAdapter(lists, this)
            val listsRecyclerView = initRecyclerView(activity).apply { adapter = listsAdapter }
            ItemTouchHelper(ListTouchListenerCallback(listsAdapter))
                    .attachToRecyclerView(listsRecyclerView)
        }
    }

    override fun onClickList(listToView: List) { (activity as? MainActivity)?.showList(listToView) }

    override fun onClickCreateList() { presenter.showCreateListDialog(activity.supportFragmentManager) }

    override fun onListSwipedToDelete(adapterPosition: Int) {
        presenter.deleteListFromBucket(adapterPosition)
        listsAdapter.notifyItemRemoved(adapterPosition)
    }

    override fun onClickRenameList(listToRename: List) {
        presenter.showRenameListDialog(activity.supportFragmentManager, listToRename)
    }
}