package com.rachelleignacio.listbucket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.listeners.ListAdapterTouchListener;
import com.rachelleignacio.listbucket.models.List;

import java.util.Collections;

/**
 * Created by rachelleignacio on 3/1/17.
 */

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder>
        implements ListAdapterTouchListener {

    private java.util.List<List> lists;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView listNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            listNameTv = (TextView) itemView.findViewById(R.id.list_name);
        }
    }

    public ListsAdapter(java.util.List<List> lists) {
        this.lists = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_main_bucket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List list = lists.get(position);
        holder.listNameTv.setText(list.getName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void onListDrag(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(lists, i, i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(lists, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onListDismiss(int position) {
        lists.remove(position);
        notifyItemRemoved(position);
    }
}
