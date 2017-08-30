package com.rachelleignacio.listbucket.presentation.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl;
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.RenameListFragmentPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.RenameListFragmentPresenterImpl;
import com.rachelleignacio.listbucket.util.Keyboard;

/**
 * Created by rachelleignacio on 4/19/17.
 */

public class RenameListDialogFragment extends DialogFragment {
    public static String TAG = "RenameListDialogFragment";

    private EditText editTextBox;
    private RenameListInteractor.Callback callback;
    private RenameListFragmentPresenter presenter;
    private List listToRename;

    @SuppressLint("ValidFragment")
    private RenameListDialogFragment() {}

    public static RenameListDialogFragment newInstance(RenameListInteractor.Callback callback,
                                                       List listToRename) {
        RenameListDialogFragment fragment = new RenameListDialogFragment();
        fragment.callback = callback;
        fragment.listToRename = listToRename;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_rename_list, null);

        editTextBox = view.findViewById(R.id.rename_list_edittext);
        editTextBox.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Keyboard.INSTANCE.show(imm);
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

        presenter = new RenameListFragmentPresenterImpl(ThreadExecutorImpl.Companion.getInstance(),
                MainThreadImpl.Companion.getInstance(), callback, DbInteractor.Companion.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        builder.setPositiveButton(getString(R.string.rename_list_dialog_submit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newName = editTextBox.getText().toString();
                if (newName.length() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.create_list_error_toast_msg),
                            Toast.LENGTH_SHORT).show();
                } else {
                    presenter.renameList(listToRename, newName);
                    Keyboard.INSTANCE.hide(imm, editTextBox.getWindowToken());
                }
            }
        });
        return builder.create();
    }
}
