package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
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

    override fun onCreateDialog(savedInstanceState: Bundle?) =
            initPresenter()
                    .run { inflateView() }
                    .applyToAlertDialogAndCreate()
                    .setDialogButtonClickListeners()

    private fun initPresenter() {
        presenter = CreateListFragmentPresenterImpl(ThreadExecutorImpl, MainThreadImpl, callback, DbInteractor)
    }

    private fun inflateView(): View =
        activity
                .layoutInflater
                .inflate(R.layout.dialog_fragment_create_list, null)
                .apply { initEditTextBox() }

    private fun View.initEditTextBox() {
        editTextBox = findViewById<EditText>(R.id.create_list_edittext).apply {
            requestFocus()
            Keyboard.show(activity, this)
            setOnEditorActionListener { _, actionId, _ ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).callOnClick()
                    handled = true
                }
                handled
            }
        }
    }

    private fun View.applyToAlertDialogAndCreate() =
            AlertDialog.Builder(activity)
                    .setButtonTextAndApplyView(this)
                    .create()

    private fun AlertDialog.Builder.setButtonTextAndApplyView(view: View) =
            apply {
                setView(view)
                setNegativeButton(getString(android.R.string.cancel), null)
                setPositiveButton(getString(R.string.create_list_dialog_submit), null)
            }

    private fun AlertDialog.setDialogButtonClickListeners() = apply {
        setOnShowListener {
            getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { dismiss() }
            getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (editTextBox.text.isEmpty()) {
                    Toast.makeText(activity, R.string.create_list_error_toast_msg, Toast.LENGTH_SHORT).show()
                } else {
                    presenter.createList(editTextBox.text.toString())
                    dismiss()
                }
            }
        }
    }

    companion object {
        const val TAG = "CreateListDialogFragment"
    }
}