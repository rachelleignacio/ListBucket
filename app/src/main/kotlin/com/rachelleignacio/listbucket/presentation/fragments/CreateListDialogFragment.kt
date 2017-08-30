package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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

/**
 * Created by rachelleignacio on 8/29/17.
 */
class CreateListDialogFragment @SuppressLint("ValidFragment") private constructor() : DialogFragment() {

    private var editTextBox: EditText? = null
    private var callback: CreateListInteractor.Callback? = null
    private var presenter: CreateListFragmentPresenter? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.dialog_fragment_create_list, null)

        editTextBox = view.findViewById(R.id.create_list_edittext)
        editTextBox!!.requestFocus()
        editTextBox!!.setOnEditorActionListener { textView, actionId, keyEvent ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_GO) {
                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).callOnClick()
                handled = true
            }
            handled
        }

        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        Keyboard.show(imm)

        presenter = CreateListFragmentPresenterImpl(ThreadExecutorImpl.instance,
                MainThreadImpl.instance, callback!!, DbInteractor.instance)

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        builder.setNegativeButton(getString(android.R.string.cancel)) { _, _ -> dismiss() }
        builder.setPositiveButton(getString(R.string.create_list_dialog_submit)) { _, _ ->
            if (editTextBox!!.text.isEmpty()) {
                Toast.makeText(activity, R.string.create_list_error_toast_msg, Toast.LENGTH_SHORT).show()
            } else {
                presenter!!.createList(editTextBox!!.text.toString())
                Keyboard.hide(imm, editTextBox!!.windowToken)
            }
        }

        return builder.create()
    }

    companion object {
        const val TAG = "CreateListDialogFragment"
        fun newInstance(callback: CreateListInteractor.Callback): CreateListDialogFragment {
            val fragment = CreateListDialogFragment()
            fragment.callback = callback
            return fragment
        }
    }
}