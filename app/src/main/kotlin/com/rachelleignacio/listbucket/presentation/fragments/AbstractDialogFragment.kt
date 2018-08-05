package com.rachelleignacio.listbucket.presentation.fragments

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.util.Keyboard

abstract class AbstractDialogFragment : DialogFragment() {
    private lateinit var editTextBox: EditText

    abstract fun initPresenterAndFields()
    abstract fun shouldDismissKeyBoardOnSubmit(): Boolean
    abstract fun onPerformCreateOrRename(newListName: String)

    @IdRes abstract fun getEditTextId(): Int
    @LayoutRes abstract fun getViewLayoutId(): Int
    @StringRes abstract fun getPositiveButtonStringId(): Int

    override fun onCreateDialog(savedInstanceState: Bundle?) =
            initPresenterAndFields()
                    .run { inflateView() }
                    .applyToAlertDialogAndCreate()
                    .setDialogButtonClickListeners()

    private fun inflateView(): View =
            activity
                    .layoutInflater
                    .inflate(getViewLayoutId(), null)
                    .apply { initEditTextBox() }

    private fun View.initEditTextBox() {
        editTextBox = findViewById<EditText>(getEditTextId()).apply {
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
                setPositiveButton(getString(getPositiveButtonStringId()), null)
            }

    private fun AlertDialog.setDialogButtonClickListeners() = apply {
        setOnShowListener {
            getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener { dismiss() }
            getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val listName = editTextBox.text.toString()
                if (listName.isEmpty()) {
                    Toast.makeText(activity, getString(R.string.create_rename_list_error_toast_msg), Toast.LENGTH_SHORT).show()
                } else {
                    if (shouldDismissKeyBoardOnSubmit()) Keyboard.hide(activity, editTextBox)
                    onPerformCreateOrRename(editTextBox.text.toString())
                    dismiss()
                }
            }
        }
    }

}