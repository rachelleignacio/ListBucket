package com.rachelleignacio.listbucket.db;

import com.rachelleignacio.listbucket.models.List;
import com.rachelleignacio.listbucket.models.ListItem;
import com.rachelleignacio.listbucket.models.ListItem_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

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

    public void deleteList(List list) {
        java.util.List<ListItem> deletedListItems = SQLite.select().from(ListItem.class)
                .where(ListItem_Table.parentList_id.eq(list.getId()))
                .queryList();
        FlowManager.getDatabase(DbFlowDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<ListItem>() {
                            @Override
                            public void processModel(ListItem item, DatabaseWrapper wrapper) {
                                item.delete();
                            }
                        }).addAll(deletedListItems).build())  // add elements (can also handle multiple)
                .build().execute();
        list.delete();
    }

    public void deleteListItem(ListItem item) {
        item.delete();
    }
}
