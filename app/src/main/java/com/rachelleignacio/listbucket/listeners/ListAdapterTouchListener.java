package com.rachelleignacio.listbucket.listeners;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public interface ListAdapterTouchListener {
    void onRowDrag(int fromPosition, int toPosition);
    void onRowDismiss(int position);
}
