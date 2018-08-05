package com.rachelleignacio.listbucket.db

import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.domain.models.ListItem
import com.rachelleignacio.listbucket.domain.models.ListItem_Table
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.processInTransactionAsync
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.kotlinextensions.update
import com.raizlabs.android.dbflow.sql.language.Select

/**
 * Created by rachelleignacio on 8/4/17.
 */
object DbInteractor {

    /**
     * Method for getting all the stored lists.
     * @return a list of Lists
     */
    fun getAllLists(): MutableList<List> = Select().from(List::class.java).queryList()

    /**
     * Method for getting all the stored items in a given list
     * @param listId the ID of the list whose items to retrieve
     * @return a list of ListItems
     */
    fun getListItems(listId: Int): MutableList<ListItem> =
            Select()
                .from(ListItem::class.java)
                .where(ListItem_Table.parentList_id.eq(listId))
                .queryList()

    fun saveList(list: List) { list.save() }

    fun saveListItem(item: ListItem) { item.save() }

    fun deleteList(list: List) {
        val deleteListItems = Select()
                .from(ListItem::class.java)
                .where(ListItem_Table.parentList_id.eq(list.id))
                .queryList()

        deleteListItems.processInTransactionAsync({ it, _ -> it.delete() })
        list.delete()
    }

    fun deleteListItem(item: ListItem) { item.delete() }

    fun renameList(list: List, newName: String) {
        list.name = newName
        list.update()
    }
}