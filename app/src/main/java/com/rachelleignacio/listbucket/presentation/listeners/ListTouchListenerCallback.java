package com.rachelleignacio.listbucket.presentation.listeners;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public class ListTouchListenerCallback extends ItemTouchHelper.Callback {
    private final ListAdapterTouchListener listTouchListener;

    public ListTouchListenerCallback(ListAdapterTouchListener listener) {
        listTouchListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        listTouchListener.onRowDrag(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listTouchListener.onRowDismiss(viewHolder.getAdapterPosition());
    }
}
