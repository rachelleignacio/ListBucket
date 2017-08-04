package com.rachelleignacio.listbucket.db

import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.domain.models.ListItem
import com.rachelleignacio.listbucket.domain.models.ListItem_Table
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.Select
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction

/**
 * Created by rachelleignacio on 8/4/17.
 */
object  DbInteractor {

    /**
     * Method for getting all the stored lists.
     * @return a list of Lists
     */
    fun getAllLists(): MutableList<List> {
        return Select().from(List::class.java).queryList()
    }

    /**
     * Method for getting all the stored items in a given list
     * @param listId the ID of the list whose items to retrieve
     * @return a list of ListItems
     */
    fun getListItems(listId: Int): MutableList<ListItem> {
        return Select()
                .from(ListItem::class.java)
                .where(ListItem_Table.parentList_id.eq(listId))
                .queryList()
    }

    fun saveList(list: List) {
        list.save()
    }

    fun saveListItem(item: ListItem) {
        item.save()
    }

    fun deleteList(list: List) {
        val deleteListItems = Select()
                .from(ListItem::class.java)
                .where(ListItem_Table.parentList_id.eq(list.getId()))
                .queryList()
        FlowManager.getDatabase(DbFlowDatabase::class.java)
                .beginTransactionAsync(ProcessModelTransaction.Builder<ListItem>(
                        ProcessModelTransaction.ProcessModel<ListItem> { item, _ -> item.delete() })
                        .addAll(deleteListItems)
                        .build())
                .build()
                .execute()
        list.delete()
    }

    fun deleteListItem(item: ListItem) {
        item.delete()
    }

    fun renameList(list: List, newName: String) {
        list.setName(newName)
        list.update()
    }
}