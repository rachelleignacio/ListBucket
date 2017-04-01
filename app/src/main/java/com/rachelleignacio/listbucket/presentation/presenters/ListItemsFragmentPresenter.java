package com.rachelleignacio.listbucket.presentation.presenters;

import com.rachelleignacio.listbucket.domain.models.ListItem;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public interface ListItemsFragmentPresenter {
    interface View {
        void showListItems(java.util.List<ListItem> items);
        void onItemSwipedToDelete(ListItem item);
    }

    void getListItems();
    void addListItem(String listItemName);
    void deleteListItem(ListItem item);
}
