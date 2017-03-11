package com.rachelleignacio.listbucket.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.interactors.DeleteListItemInteractor;
import com.rachelleignacio.listbucket.interactors.impl.DeleteListItemInteractorImpl;
import com.rachelleignacio.listbucket.listeners.ListAdapterTouchListener;
import com.rachelleignacio.listbucket.models.ListItem;

import java.util.Collections;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ViewHolder>
        implements ListAdapterTouchListener {
    private java.util.List<ListItem> listItems;
    private DeleteListItemInteractor.Callback deleteListItemCallback;

    public ListItemsAdapter(java.util.List<ListItem> listItems,
                            DeleteListItemInteractor.Callback deleteListItemCallback) {
        this.listItems = listItems;
        this.deleteListItemCallback = deleteListItemCallback;
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
        DeleteListItemInteractor deleteListItemInteractor =
                new DeleteListItemInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                        deleteListItemCallback, DbInteractor.getInstance(), listItems.get(position));
        deleteListItemInteractor.execute();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView listItemNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            listItemNameTv = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}
