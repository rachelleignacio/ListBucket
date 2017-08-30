package com.rachelleignacio.listbucket.presentation.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.domain.models.ListItem;
import com.rachelleignacio.listbucket.presentation.adapters.ListItemsAdapter;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl;
import com.rachelleignacio.listbucket.presentation.listeners.ListTouchListenerCallback;
import com.rachelleignacio.listbucket.presentation.presenters.ListItemsFragmentPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.ListItemsFragmentPresenterImpl;
import com.rachelleignacio.listbucket.util.Keyboard;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class ListItemsFragment extends Fragment implements ListItemsFragmentPresenter.View {
    private ListItemsFragmentPresenter presenter;
    private List parentList;
    private ListItemsAdapter itemsAdapter;

    @SuppressLint("ValidFragment")
    private ListItemsFragment() {}

    public static ListItemsFragment newInstance(List list) {
        ListItemsFragment fragment = new ListItemsFragment();
        fragment.parentList = list;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_items_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ListItemsFragmentPresenterImpl(ThreadExecutorImpl.Companion.getInstance(),
                MainThreadImpl.Companion.getInstance(), DbInteractor.Companion.getInstance(), this, parentList);

        getActivity().setTitle(parentList.getName());
        initListItems();
        initAddListItemView();
    }

    private void initListItems() {
        presenter.getListItems();
    }

    private void initAddListItemView() {
        final EditText addListItemTextbox = getActivity().findViewById(R.id.add_list_item_edittext);

        addListItemTextbox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (addListItemTextbox.getText().length() > 0) {
                        presenter.addListItem(addListItemTextbox.getText().toString());
                        itemsAdapter.notifyItemInserted(presenter.getListCount());
                        addListItemTextbox.setText("");
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.add_list_item_error_toast_msg),
                                Toast.LENGTH_SHORT).show();
                    }
                    handled = true;
                }
                return handled;
            }
        });
        addListItemTextbox.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Keyboard.INSTANCE.show(imm);
    }

    @Override
    public void showListItems(java.util.List<ListItem> items) {
        RecyclerView itemsRecyclerView = getActivity().findViewById(R.id.recycler_view_items);
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemsRecyclerView.setAdapter(null);

        itemsAdapter = new ListItemsAdapter(items, this);
        itemsRecyclerView.setAdapter(itemsAdapter);

        ItemTouchHelper.Callback itemTouchCallback = new ListTouchListenerCallback(itemsAdapter);
        ItemTouchHelper itemTouchListener = new ItemTouchHelper(itemTouchCallback);
        itemTouchListener.attachToRecyclerView(itemsRecyclerView);
    }

    @Override
    public void onItemSwipedToDelete(int position) {
        presenter.deleteListItem(position);
        itemsAdapter.notifyItemRemoved(position);
    }
}
