package com.rachelleignacio.listbucket.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.presentation.listeners.ListAdapterTouchListener;
import com.rachelleignacio.listbucket.domain.models.ListItem;
import com.rachelleignacio.listbucket.presentation.presenters.ListItemsFragmentPresenter;

import java.util.Collections;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ViewHolder>
        implements ListAdapterTouchListener {
    private java.util.List<ListItem> listItems;
    private ListItemsFragmentPresenter.View view;

    public ListItemsAdapter(java.util.List<ListItem> listItems, ListItemsFragmentPresenter.View view) {
        this.listItems = listItems;
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.listItemNameTv.setText(listItem.getName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onRowDrag(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listItems, i, i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listItems, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowDismiss(int position) {
        view.onItemSwipedToDelete(listItems.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView listItemNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            listItemNameTv = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}
