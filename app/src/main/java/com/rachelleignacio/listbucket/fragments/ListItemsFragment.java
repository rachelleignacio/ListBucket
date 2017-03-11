package com.rachelleignacio.listbucket.fragments;

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
import com.rachelleignacio.listbucket.adapters.ListItemsAdapter;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.interactors.AddListItemInteractor;
import com.rachelleignacio.listbucket.interactors.DeleteListItemInteractor;
import com.rachelleignacio.listbucket.interactors.GetAllListItemsInteractor;
import com.rachelleignacio.listbucket.interactors.impl.AddListItemInteractorImpl;
import com.rachelleignacio.listbucket.interactors.impl.GetAllListItemsInteractorImpl;
import com.rachelleignacio.listbucket.listeners.ListTouchListenerCallback;
import com.rachelleignacio.listbucket.listeners.OnStartDragListener;
import com.rachelleignacio.listbucket.models.List;
import com.rachelleignacio.listbucket.models.ListItem;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class ListItemsFragment extends Fragment implements GetAllListItemsInteractor.Callback,
        AddListItemInteractor.Callback, DeleteListItemInteractor.Callback, OnStartDragListener {

    private List parentList;
    private ItemTouchHelper itemTouchListener;

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
        initListItems();
        initAddListItemView();
    }

    private void initListItems() {
        GetAllListItemsInteractor getItemsInteractor = new GetAllListItemsInteractorImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                ListItemsFragment.this,
                DbInteractor.getInstance(),
                parentList.getId());
        getItemsInteractor.execute();
    }

    private void initAddListItemView() {
        CardView addListItemView = (CardView) getActivity().findViewById(R.id.add_list_item_cardview);
        final EditText addListItemTextbox = (EditText) getActivity().findViewById(R.id.add_list_item_edittext);

        addListItemView.setVisibility(View.VISIBLE);
        addListItemView.findViewById(R.id.add_list_item_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addListItemTextbox.getText().length() > 0) {
                    AddListItemInteractor addItemInteractor = new AddListItemInteractorImpl(ThreadExecutor.getInstance(),
                            MainThreadImpl.getInstance(),
                            ListItemsFragment.this,
                            DbInteractor.getInstance(),
                            parentList,
                            addListItemTextbox.getText().toString());
                    addItemInteractor.execute();
                    addListItemTextbox.setText("");
                } else {
                    Toast.makeText(getActivity(), getString(R.string.add_list_item_error_toast_msg),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onListItemsRetrieved(java.util.List<ListItem> items) {
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
    public void onListItemAdded() {
        refreshFragment();
    }

    @Override
    public void onListItemDeleted() {
        refreshFragment();
    }

    private void refreshFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchListener.startDrag(viewHolder);
    }
}
