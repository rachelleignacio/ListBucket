package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.presenters.RenameListFragmentPresenter
import com.rachelleignacio.listbucket.presentation.presenters.impl.RenameListFragmentPresenterImpl

private const val LIST_TO_RENAME_ARG = "listToRename"
fun newRenameListDialogFragmentInstance(callback: RenameListInteractor.Callback, listToRename: List): RenameListDialogFragment =
        RenameListDialogFragment().apply {
            this.callback = callback
            arguments = Bundle(1).apply { putSerializable(LIST_TO_RENAME_ARG, listToRename) }
        }

/**
 * Created by rachelleignacio on 8/30/17.
 */
class RenameListDialogFragment @SuppressLint("ValidFragment") internal constructor() : AbstractDialogFragment() {
    internal lateinit var callback: RenameListInteractor.Callback
    private lateinit var presenter: RenameListFragmentPresenter
    private lateinit var listToRename: List

    @IdRes override fun getEditTextId() = R.id.rename_list_edittext
    @LayoutRes override fun getViewLayoutId() = R.layout.dialog_fragment_rename_list
    @StringRes override fun getPositiveButtonStringId() = R.string.rename_list_dialog_submit

    override fun initPresenterAndFields() {
        listToRename = arguments.getSerializable(LIST_TO_RENAME_ARG) as List
        presenter = RenameListFragmentPresenterImpl(ThreadExecutorImpl, MainThreadImpl, callback, DbInteractor)
    }

    override fun shouldDismissKeyBoardOnSubmit() = true

    override fun onPerformCreateOrRename(newListName: String) { presenter.renameList(listToRename, newListName) }

    companion object {
        const val TAG = "RenameListDialogFragment"
    }
}