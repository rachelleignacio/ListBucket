package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor
import com.rachelleignacio.listbucket.presentation.presenters.CreateListFragmentPresenter
import com.rachelleignacio.listbucket.presentation.presenters.impl.CreateListFragmentPresenterImpl

fun newCreateListDialogFragmentInstance(callback: CreateListInteractor.Callback): CreateListDialogFragment =
    CreateListDialogFragment().apply { this.callback = callback }

/**
 * Created by rachelleignacio on 8/29/17.
 */
class CreateListDialogFragment @SuppressLint("ValidFragment") internal constructor() : AbstractDialogFragment() {
    internal lateinit var callback: CreateListInteractor.Callback
    private lateinit var presenter: CreateListFragmentPresenter

    @IdRes override fun getEditTextId() = R.id.create_list_edittext
    @LayoutRes override fun getViewLayoutId() = R.layout.dialog_fragment_create_list
    @StringRes override fun getPositiveButtonStringId() = R.string.create_list_dialog_submit

    override fun initPresenterAndFields() {
        presenter = CreateListFragmentPresenterImpl(ThreadExecutorImpl, MainThreadImpl, callback, DbInteractor)
    }

    override fun shouldDismissKeyBoardOnSubmit() = false

    override fun onPerformCreateOrRename(newListName: String) { presenter.createList(newListName) }

    companion object {
        const val TAG = "CreateListDialogFragment"
    }
}