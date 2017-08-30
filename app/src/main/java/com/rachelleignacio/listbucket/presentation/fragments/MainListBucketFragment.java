package com.rachelleignacio.listbucket.presentation.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.activities.MainActivity;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl;
import com.rachelleignacio.listbucket.presentation.adapters.ListsAdapter;
import com.rachelleignacio.listbucket.presentation.listeners.ListTouchListenerCallback;
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.ListBucketFragmentPresenterImpl;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class MainListBucketFragment extends Fragment implements ListBucketFragmentPresenter.View {

    private ItemTouchHelper listTouchListener;
    private ListBucketFragmentPresenter presenter;
    ListsAdapter listsAdapter;

    @SuppressLint("ValidFragment")
    private MainListBucketFragment() {
    }

    public static MainListBucketFragment newInstance() {
        return new MainListBucketFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_bucket_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ListBucketFragmentPresenterImpl(ThreadExecutorImpl.Companion.getInstance(),
                MainThreadImpl.Companion.getInstance(), DbInteractor.INSTANCE, this);
        showFabAddButton();
        initLists();
    }

    private void initLists() {
        presenter.getLists();
    }

    @Override
    public void showLists(java.util.List<List> lists) {
        RecyclerView listsRecyclerView = getActivity().findViewById(R.id.recycler_view_items);
        listsRecyclerView.setHasFixedSize(true);
        listsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listsRecyclerView.setAdapter(null);

        listsAdapter = new ListsAdapter(lists, this);
        listsRecyclerView.setAdapter(listsAdapter);

        ItemTouchHelper.Callback listTouchCallback = new ListTouchListenerCallback(listsAdapter);
        listTouchListener = new ItemTouchHelper(listTouchCallback);
        listTouchListener.attachToRecyclerView(listsRecyclerView);
    }

    @Override
    public void onClickList(List listToView) {
        ((MainActivity) getActivity()).showList(listToView);
    }

    @Override
    public void onClickCreateList() {
        presenter.showCreateListDialog(getActivity().getSupportFragmentManager());
    }

    @Override
    public void onListSwipedToDelete(int position) {
        presenter.deleteListFromBucket(position);
        listsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onClickRenameList(List listToRename) {
        presenter.showRenameListDialog(getActivity().getSupportFragmentManager(), listToRename);
    }

    private void showFabAddButton() {
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        if (fab != null) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickCreateList();
                }
            });
        }
    }
}
