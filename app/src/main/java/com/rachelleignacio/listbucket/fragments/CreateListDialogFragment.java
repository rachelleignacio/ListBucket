package com.rachelleignacio.listbucket.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.interactors.CreateListInteractor;
import com.rachelleignacio.listbucket.interactors.impl.CreateListInteractorImpl;

/**
 * Created by rachelleignacio on 3/6/17.
 */

public class CreateListDialogFragment extends DialogFragment {
    public static String TAG = "CreateListDialogFragment";

    private EditText editTextBox;
    private CreateListInteractor.Callback callback;

    public static CreateListDialogFragment newInstance(CreateListInteractor.Callback callback) {
        CreateListDialogFragment fragment = new CreateListDialogFragment();
        fragment.callback = callback;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_create_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        editTextBox = (EditText) getView().findViewById(R.id.create_list_name_edittext);

        getView().findViewById(R.id.create_list_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextBox.getText().length() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.create_list_error_toast_msg),
                            Toast.LENGTH_SHORT).show();
                } else {
                    new CreateListInteractorImpl(ThreadExecutor.getInstance(),
                            MainThreadImpl.getInstance(), callback,
                            editTextBox.getText().toString()).execute();
                    dismiss();
                }
            }
        });
    }
}
