package com.rachelleignacio.listbucket.db;

import android.support.annotation.NonNull;

import com.rachelleignacio.listbucket.models.ListItem;
import com.rachelleignacio.listbucket.models.ListItem_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.List;

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

    public java.util.List<ListItem> getListItems(int listId) {
        return SQLite.select().from(ListItem.class)
            .where(ListItem_Table.parentList_id.eq(listId))
            .queryList();
    }
}
