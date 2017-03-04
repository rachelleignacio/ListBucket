package com.rachelleignacio.listbucket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.models.List;

/**
 * Created by rachelleignacio on 3/1/17.
 */

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {
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
}
