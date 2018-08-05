package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.constants.SharedPrefs
import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.domain.models.ListItem
import com.rachelleignacio.listbucket.presentation.activities.MainActivity
import com.rachelleignacio.listbucket.presentation.adapters.ListItemsAdapter
import com.rachelleignacio.listbucket.presentation.listeners.ListTouchListenerCallback
import com.rachelleignacio.listbucket.presentation.presenters.ListItemsFragmentPresenter
import com.rachelleignacio.listbucket.presentation.presenters.impl.ListItemsFragmentPresenterImpl
import com.rachelleignacio.listbucket.util.Keyboard
import com.rachelleignacio.listbucket.util.Prefs
import com.rachelleignacio.listbucket.util.Prefs.get

private const val CURRENT_LIST_ARG = "currentList"
fun newItemsInstance(parentList: List): ListItemsFragment =
        ListItemsFragment().apply { arguments = Bundle().apply { putSerializable(CURRENT_LIST_ARG, parentList) } }

/**
 * Created by rachelleignacio on 8/30/17.
 */
class ListItemsFragment @SuppressLint("ValidFragment") internal constructor() : Fragment(),
        ListItemsFragmentPresenter.View {

    private lateinit var presenter: ListItemsFragmentPresenter
    private lateinit var parentList: List
    private lateinit var itemsAdapter: ListItemsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_list_items_layout, container, false)

    private fun readBundleArgs() {
        if (arguments != null) {
            parentList = arguments.getSerializable(CURRENT_LIST_ARG) as List
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readBundleArgs()
        presenter = ListItemsFragmentPresenterImpl(ThreadExecutorImpl.instance, MainThreadImpl.instance,
                DbInteractor, this, parentList)

        activity.title = parentList.name
        initListItems()
        initAddListItemView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putSerializable(CURRENT_LIST_ARG, parentList)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        if (Prefs.get(activity)[SharedPrefs.SCREEN_AWAKE_KEY, false] as Boolean) {
            (activity as MainActivity).setScreenAlwaysOn(true)
        }
    }

    override fun onStop() {
        super.onStop()
        // clear FLAG_KEEP_SCREEN_ON when navigating away from list detail view
        if (Prefs.get(activity)[SharedPrefs.SCREEN_AWAKE_KEY, false] as Boolean) {
            (activity as MainActivity).setScreenAlwaysOn(false)
        }
    }

    private fun initListItems() {
        presenter.getListItems()
    }

    private fun initAddListItemView() {
        val addListItemTextBox = activity.findViewById<EditText>(R.id.add_list_item_edittext)
        addListItemTextBox.requestFocus()
        Keyboard.show(activity, addListItemTextBox)
        addListItemTextBox.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (addListItemTextBox.text.isNotEmpty()) {
                    presenter.addListItem(addListItemTextBox.text.toString())
                    itemsAdapter.notifyItemInserted(presenter.listCount)
                    addListItemTextBox.setText("")
                } else {
                    Toast.makeText(activity, getString(R.string.add_list_item_error_toast_msg),
                            Toast.LENGTH_SHORT).show()
                }
                handled = true
            }
            handled
        }

        addListItemTextBox.requestFocus()
    }

    override fun showListItems(items: MutableList<ListItem>) {
        val itemsRecyclerView = activity.findViewById<RecyclerView>(R.id.recycler_view_items)
        itemsRecyclerView.setHasFixedSize(true)
        itemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        itemsRecyclerView.adapter = null

        itemsAdapter = ListItemsAdapter(items, this)
        itemsRecyclerView.adapter = itemsAdapter

        val itemTouchCallback = ListTouchListenerCallback(itemsAdapter)
        val itemTouchListener = ItemTouchHelper(itemTouchCallback)
        itemTouchListener.attachToRecyclerView(itemsRecyclerView)
    }

    override fun onItemSwipedToDelete(adapterPosition: Int) {
        presenter.deleteListItem(adapterPosition)
        itemsAdapter.notifyItemRemoved(adapterPosition)
    }
}