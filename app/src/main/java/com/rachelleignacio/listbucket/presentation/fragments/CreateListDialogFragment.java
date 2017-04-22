package com.rachelleignacio.listbucket.presentation.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.domain.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.presentation.presenters.CreateListFragmentPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.CreateListFragmentPresenterImpl;
import com.rachelleignacio.listbucket.presentation.presenters.impl.RenameListFragmentPresenterImpl;

/**
 * Created by rachelleignacio on 3/6/17.
 */

public class CreateListDialogFragment extends DialogFragment {
    public static String TAG = "CreateListDialogFragment";

    private EditText editTextBox;
    private CreateListInteractor.Callback callback;
    private CreateListFragmentPresenter presenter;

    @SuppressLint("ValidFragment")
    private CreateListDialogFragment() {}

    public static CreateListDialogFragment newInstance(CreateListInteractor.Callback callback) {
        CreateListDialogFragment fragment = new CreateListDialogFragment();
        fragment.callback = callback;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_create_list, null);

        editTextBox = (EditText) view.findViewById(R.id.create_list_edittext);
        editTextBox.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        showKeyboard(imm);
        editTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).callOnClick();
                    handled = true;
                }
                return handled;
            }
        });

        presenter = new CreateListFragmentPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), callback, DbInteractor.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        builder.setPositiveButton(getString(R.string.create_list_dialog_submit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (editTextBox.getText().length() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.create_list_error_toast_msg),
                            Toast.LENGTH_SHORT).show();
                } else {
                    presenter.createList(editTextBox.getText().toString());
                    hideKeyboard(imm);
                }
            }
        });
        return builder.create();
    }

    private void showKeyboard(InputMethodManager imm) {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void hideKeyboard(InputMethodManager imm) {
        imm.hideSoftInputFromWindow(editTextBox.getWindowToken(), 0);
    }
}
