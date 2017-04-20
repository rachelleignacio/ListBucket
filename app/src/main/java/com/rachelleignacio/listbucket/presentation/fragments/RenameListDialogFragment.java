package com.rachelleignacio.listbucket.presentation.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.domain.interactors.RenameListInteractor;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.RenameListFragmentPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.RenameListFragmentPresenterImpl;

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
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_rename_list, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        editTextBox = (EditText) view.findViewById(R.id.rename_list_edittext);
        presenter = new RenameListFragmentPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), callback, DbInteractor.getInstance());

        builder.setView(view);
//        builder.setTitle(String.format(getString(R.string.rename_list_dialog_title), listToRename.getName()));
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
                }
            }
        });
        return builder.create();
    }
}
