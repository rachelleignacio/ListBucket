package com.rachelleignacio.listbucket.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.presentation.adapters.ListsAdapter;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.presentation.listeners.ListTouchListenerCallback;
import com.rachelleignacio.listbucket.presentation.listeners.OnStartDragListener;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.ListBucketFragmentPresenterImpl;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class MainListBucketFragment extends Fragment
        implements ListBucketFragmentPresenter.View, OnStartDragListener {

    private ItemTouchHelper listTouchListener;
    private ListBucketFragmentPresenter presenter;

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

        presenter = new ListBucketFragmentPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), DbInteractor.getInstance(), this);

        initLists();
    }

    private void initLists() {
        presenter.getLists();
    }

    @Override
    public void showLists(java.util.List<List> lists) {
        RecyclerView listsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_items);
        listsRecyclerView.setHasFixedSize(true);
        listsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ListsAdapter listsAdapter = new ListsAdapter(getActivity(), lists);
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
