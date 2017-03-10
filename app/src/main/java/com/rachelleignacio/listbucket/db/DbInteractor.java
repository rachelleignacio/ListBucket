package com.rachelleignacio.listbucket.db;

import com.rachelleignacio.listbucket.models.List;
import com.rachelleignacio.listbucket.models.ListItem;
import com.rachelleignacio.listbucket.models.ListItem_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by rachelleignacio on 3/6/17.
 */

public class DbInteractor {
    private static final DbInteractor instance = new DbInteractor();

    public static DbInteractor getInstance() {
        return instance;
    }

    private DbInteractor() {
    }

    /**
     * Method for getting all the stored lists.
     * @return a list of Lists
     */
    public java.util.List<com.rachelleignacio.listbucket.models.List> getAllLists() {
        return SQLite.select().from(com.rachelleignacio.listbucket.models.List.class).queryList();
    }

    /**
     * Method for getting all the stored items in a given list
     * @param listId the ID of the list whose items to retrieve
     * @return a list of ListItems
     */
    public java.util.List<ListItem> getListItems(int listId) {
        return SQLite.select().from(ListItem.class)
            .where(ListItem_Table.parentList_id.eq(listId))
            .queryList();
    }

    public void saveList(List list) {
        list.save();
    }

    public void saveListItem(ListItem item) {
        item.save();
    }
}
