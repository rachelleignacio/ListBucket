package com.rachelleignacio.listbucket.presentation.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
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
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.presenters.RenameListFragmentPresenter
import com.rachelleignacio.listbucket.presentation.presenters.impl.RenameListFragmentPresenterImpl
import com.rachelleignacio.listbucket.util.Keyboard

/**
 * Created by rachelleignacio on 8/30/17.
 */
class RenameListDialogFragment @SuppressLint("ValidFragment") private constructor() : DialogFragment() {
    private lateinit var editTextBox: EditText
    private lateinit var callback: RenameListInteractor.Callback
    private lateinit var presenter: RenameListFragmentPresenter
    private lateinit var listToRename: List

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater!!.inflate(R.layout.dialog_fragment_rename_list, null)

        editTextBox = view.findViewById(R.id.rename_list_edittext)
        editTextBox.requestFocus()
        editTextBox.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_GO) {
                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).callOnClick()
                handled = true
            }
            handled
        }

        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        Keyboard.show(imm)

        presenter = RenameListFragmentPresenterImpl(ThreadExecutorImpl.instance, MainThreadImpl.instance,
                callback, DbInteractor.instance)

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        builder.setNegativeButton(getString(android.R.string.cancel)) { _, _ -> dismiss() }
        builder.setPositiveButton(getString(R.string.rename_list_dialog_submit)) { _, _ ->
            val newName = editTextBox.text.toString()
            if (newName.isEmpty()) {
                Toast.makeText(activity, getString(R.string.create_list_error_toast_msg),
                        Toast.LENGTH_SHORT).show()
            } else {
                Keyboard.hide(imm, editTextBox.windowToken)
                presenter.renameList(listToRename, newName)
            }
        }

        return builder.create()
    }

    companion object {
        val TAG = "RenameListDialogFragment"
        fun newInstance(callback: RenameListInteractor.Callback, listToRename: List):
                RenameListDialogFragment {
            val fragment = RenameListDialogFragment()
            fragment.callback = callback
            fragment.listToRename = listToRename
            return fragment
        }
    }
}