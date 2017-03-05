package com.rachelleignacio.listbucket.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.adapters.ListItemsAdapter;
import com.rachelleignacio.listbucket.listeners.ListTouchListenerCallback;
import com.rachelleignacio.listbucket.listeners.OnStartDragListener;
import com.rachelleignacio.listbucket.util.MockDataUtil;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class ListItemsFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper itemTouchListener;

    public static ListItemsFragment newInstance(int listId) {
        return new ListItemsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListItems();
    }

    private void initListItems() {
        RecyclerView itemsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_items);
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ListItemsAdapter itemsAdapter = new ListItemsAdapter(MockDataUtil.getListItems());
        itemsRecyclerView.setAdapter(itemsAdapter);

        ItemTouchHelper.Callback itemTouchCallback = new ListTouchListenerCallback(itemsAdapter);
        itemTouchListener = new ItemTouchHelper(itemTouchCallback);
        itemTouchListener.attachToRecyclerView(itemsRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchListener.startDrag(viewHolder);
    }
}
