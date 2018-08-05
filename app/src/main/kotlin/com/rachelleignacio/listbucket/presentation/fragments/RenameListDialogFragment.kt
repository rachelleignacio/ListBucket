package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.db.DbInteractor
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.presenters.RenameListFragmentPresenter
import com.rachelleignacio.listbucket.presentation.presenters.impl.RenameListFragmentPresenterImpl
import com.rachelleignacio.listbucket.util.Keyboard

private const val LIST_TO_RENAME_ARG = "listToRename"
fun newRenameListDialogFragmentInstance(callback: RenameListInteractor.Callback, listToRename: List): RenameListDialogFragment =
        RenameListDialogFragment().apply {
            this.callback = callback
            arguments = Bundle(1).apply { putSerializable(LIST_TO_RENAME_ARG, listToRename) }
        }

/**
 * Created by rachelleignacio on 8/30/17.
 */
class RenameListDialogFragment @SuppressLint("ValidFragment") internal constructor() : DialogFragment() {
    private lateinit var editTextBox: EditText
    internal lateinit var callback: RenameListInteractor.Callback
    private lateinit var presenter: RenameListFragmentPresenter
    private lateinit var listToRename: List

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_fragment_rename_list, null)

        editTextBox = view.findViewById(R.id.rename_list_edittext)
        editTextBox.requestFocus()
        Keyboard.show(activity, editTextBox)
        editTextBox.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_GO) {
                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).callOnClick()
                handled = true
            }
            handled
        }

        listToRename = arguments.getSerializable(LIST_TO_RENAME_ARG) as List
        presenter = RenameListFragmentPresenterImpl(ThreadExecutorImpl, MainThreadImpl, callback, DbInteractor)

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        builder.setNegativeButton(getString(android.R.string.cancel), null)
        builder.setPositiveButton(getString(R.string.rename_list_dialog_submit), null)

        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { dismiss() }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val newName = editTextBox.text.toString()
                if (newName.isEmpty()) {
                    Toast.makeText(activity, getString(R.string.create_list_error_toast_msg),
                            Toast.LENGTH_SHORT).show()
                } else {
                    Keyboard.hide(activity, editTextBox)
                    presenter.renameList(listToRename, newName)
                    dismiss()
                }
            }
        }

        return dialog
    }

    companion object {
        const val TAG = "RenameListDialogFragment"
    }
}