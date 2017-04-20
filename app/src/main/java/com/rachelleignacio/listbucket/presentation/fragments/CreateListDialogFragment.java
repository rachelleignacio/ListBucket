package com.rachelleignacio.listbucket.presentation.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
        presenter = new CreateListFragmentPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), callback, DbInteractor.getInstance());

        getView().findViewById(R.id.create_list_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextBox.getText().length() == 0) {
                    Toast.makeText(getActivity(), getString(R.string.create_list_error_toast_msg),
                            Toast.LENGTH_SHORT).show();
                } else {
                    presenter.createList(editTextBox.getText().toString());
                    dismiss();
                }
            }
        });
        editTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    getView().findViewById(R.id.create_list_button).callOnClick();
                    handled = true;
                }
                return handled;
            }
        });
    }
}
