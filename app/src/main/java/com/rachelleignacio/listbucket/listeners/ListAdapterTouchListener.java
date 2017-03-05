package com.rachelleignacio.listbucket.listeners;

/**
 * Created by rachelleignacio on 3/4/17.
 */

public interface ListAdapterTouchListener {
    void onListDrag(int fromPosition, int toPosition);
    void onListDismiss(int position);
}
