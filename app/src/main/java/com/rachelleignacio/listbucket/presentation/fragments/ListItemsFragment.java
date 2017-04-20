package com.rachelleignacio.listbucket.presentation.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.presentation.adapters.ListItemsAdapter;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.presentation.listeners.ListTouchListenerCallback;
import com.rachelleignacio.listbucket.presentation.listeners.OnStartDragListener;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.domain.models.ListItem;
import com.rachelleignacio.listbucket.presentation.presenters.ListItemsFragmentPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.ListItemsFragmentPresenterImpl;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class ListItemsFragment extends Fragment implements ListItemsFragmentPresenter.View,
        OnStartDragListener {

    private List parentList;
    private ItemTouchHelper itemTouchListener;
    private ListItemsFragmentPresenter presenter;

    @SuppressLint("ValidFragment")
    private ListItemsFragment() {}

    public static ListItemsFragment newInstance(List list) {
        ListItemsFragment fragment = new ListItemsFragment();
        fragment.parentList = list;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ListItemsFragmentPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), DbInteractor.getInstance(), this, parentList);

        initListItems();
        initAddListItemView();
    }

    private void initListItems() {
        presenter.getListItems();
    }

    private void initAddListItemView() {
        CardView addListItemView = (CardView) getActivity().findViewById(R.id.add_list_item_cardview);
        final EditText addListItemTextbox = (EditText) getActivity().findViewById(R.id.add_list_item_edittext);

        addListItemView.setVisibility(View.VISIBLE);
        addListItemView.findViewById(R.id.add_list_item_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addListItemTextbox.getText().length() > 0) {
                    presenter.addListItem(addListItemTextbox.getText().toString());
                    addListItemTextbox.setText("");
                } else {
                    Toast.makeText(getActivity(), getString(R.string.add_list_item_error_toast_msg),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void showListItems(java.util.List<ListItem> items) {
        RecyclerView itemsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_items);
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ListItemsAdapter itemsAdapter = new ListItemsAdapter(items, this);
        itemsRecyclerView.setAdapter(itemsAdapter);

        ItemTouchHelper.Callback itemTouchCallback = new ListTouchListenerCallback(itemsAdapter);
        itemTouchListener = new ItemTouchHelper(itemTouchCallback);
        itemTouchListener.attachToRecyclerView(itemsRecyclerView);
    }

    @Override
    public void onItemSwipedToDelete(ListItem item) {
        presenter.deleteListItem(item);
    }

    public void refreshFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchListener.startDrag(viewHolder);
    }
}
