package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
fun newListItemsFragmentInstance(parentList: List): ListItemsFragment =
        ListItemsFragment().apply { arguments = Bundle(1).apply { putSerializable(CURRENT_LIST_ARG, parentList) } }

/**
 * Created by rachelleignacio on 8/30/17.
 */
class ListItemsFragment @SuppressLint("ValidFragment") internal constructor() : Fragment(), ListItemsFragmentPresenter.View {

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
        presenter = ListItemsFragmentPresenterImpl(ThreadExecutorImpl, MainThreadImpl, DbInteractor, this, parentList)

        activity.title = parentList.name
        initListItems()
        initAddListItemView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(CURRENT_LIST_ARG, parentList)
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

    private fun initListItems() { presenter.getListItems() }

    private fun initAddListItemView() {
        with(activity.findViewById<EditText>(R.id.add_list_item_edittext)) {
            requestFocus()
            Keyboard.show(activity, this)
            setOnEditorActionListener { _, actionId, _ ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (text.isNotEmpty()) {
                        presenter.addListItem(text.toString())
                        itemsAdapter.notifyItemInserted(presenter.listCount)
                        setText("")
                    } else {
                        Toast.makeText(activity, getString(R.string.add_list_item_error_toast_msg),
                                Toast.LENGTH_SHORT).show()
                    }
                    handled = true
                }
                handled
            }
        }
    }

    override fun showListItems(items: MutableList<ListItem>) {
        if (activity != null) {
            itemsAdapter = ListItemsAdapter(items, this)
            val itemsRecyclerView = initRecyclerView(activity).apply { adapter = itemsAdapter }
            ItemTouchHelper(ListTouchListenerCallback(itemsAdapter))
                    .attachToRecyclerView(itemsRecyclerView)
        }
    }

    override fun onItemSwipedToDelete(adapterPosition: Int) {
        presenter.deleteListItem(adapterPosition)
        itemsAdapter.notifyItemRemoved(adapterPosition)
    }
}