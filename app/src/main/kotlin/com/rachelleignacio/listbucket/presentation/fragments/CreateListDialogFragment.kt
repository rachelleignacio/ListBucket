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
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor
import com.rachelleignacio.listbucket.presentation.presenters.CreateListFragmentPresenter
import com.rachelleignacio.listbucket.presentation.presenters.impl.CreateListFragmentPresenterImpl
import com.rachelleignacio.listbucket.util.Keyboard

fun newCreateListDialogFragmentInstance(callback: CreateListInteractor.Callback): CreateListDialogFragment =
    CreateListDialogFragment().apply { this.callback = callback }

/**
 * Created by rachelleignacio on 8/29/17.
 */
class CreateListDialogFragment @SuppressLint("ValidFragment") internal constructor() : DialogFragment() {

    private lateinit var editTextBox: EditText
    internal lateinit var callback: CreateListInteractor.Callback
    private lateinit var presenter: CreateListFragmentPresenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_fragment_create_list, null)

        editTextBox = view.findViewById(R.id.create_list_edittext)
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


        presenter = CreateListFragmentPresenterImpl(ThreadExecutorImpl, MainThreadImpl, callback, DbInteractor)

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        builder.setNegativeButton(getString(android.R.string.cancel), null)
        builder.setPositiveButton(getString(R.string.create_list_dialog_submit), null)

        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { dismiss() }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (editTextBox.text.isEmpty()) {
                    Toast.makeText(activity, R.string.create_list_error_toast_msg, Toast.LENGTH_SHORT).show()
                } else {
                    presenter.createList(editTextBox.text.toString())
                    dismiss()
                }
            }
        }

        return dialog
    }

    companion object {
        const val TAG = "CreateListDialogFragment"
    }
}