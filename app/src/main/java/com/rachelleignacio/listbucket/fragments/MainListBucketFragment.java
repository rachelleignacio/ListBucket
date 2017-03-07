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
import com.rachelleignacio.listbucket.adapters.ListsAdapter;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.listeners.ListTouchListenerCallback;
import com.rachelleignacio.listbucket.listeners.OnStartDragListener;
import com.rachelleignacio.listbucket.util.MockDataUtil;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class MainListBucketFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper listTouchListener;

    public static MainListBucketFragment newInstance() {
        return new MainListBucketFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLists();
    }

    private void initLists() {
        RecyclerView listsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_items);
        listsRecyclerView.setHasFixedSize(true);
        listsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ListsAdapter listsAdapter = new ListsAdapter(getActivity(), DbInteractor.getInstance().getAllLists());
        listsRecyclerView.setAdapter(listsAdapter);

        ItemTouchHelper.Callback listTouchCallback = new ListTouchListenerCallback(listsAdapter);
        listTouchListener = new ItemTouchHelper(listTouchCallback);
        listTouchListener.attachToRecyclerView(listsRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        listTouchListener.startDrag(viewHolder);
    }
}
