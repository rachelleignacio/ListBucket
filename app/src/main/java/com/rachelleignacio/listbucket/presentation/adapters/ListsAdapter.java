package com.rachelleignacio.listbucket.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.listeners.ListAdapterTouchListener;
import com.rachelleignacio.listbucket.presentation.presenters.ListBucketFragmentPresenter;

/**
 * Created by rachelleignacio on 3/1/17.
 */

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder>
        implements ListAdapterTouchListener {

    private java.util.List<List> lists;
    private ListBucketFragmentPresenter.View view;

    public ListsAdapter(java.util.List<List> lists, ListBucketFragmentPresenter.View view) {
        this.lists = lists;
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bucket_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final List list = lists.get(position);
        holder.listNameTv.setText(list.getName());
        holder.renameListIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListsAdapter.this.view.onClickRenameList(list);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListsAdapter.this.view.onClickList(list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    @Override
    public void onRowDismiss(int position) {
        ListsAdapter.this.view.onListSwipedToDelete(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listNameTv;
        ImageView renameListIv;

        ViewHolder(View itemView) {
            super(itemView);
            listNameTv = itemView.findViewById(R.id.list_name);
            renameListIv = itemView.findViewById(R.id.rename_list);
        }
    }
}
