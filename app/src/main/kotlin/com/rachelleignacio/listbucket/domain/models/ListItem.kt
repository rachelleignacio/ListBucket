package com.rachelleignacio.listbucket.domain.models

import com.rachelleignacio.listbucket.db.DbFlowDatabase
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

/**
 * Created by rachelleignacio on 8/4/17.
 */
@Table(database = DbFlowDatabase::class,
        allFields =  true,
        orderedCursorLookUp = true,
        assignDefaultValuesFromCursor = false,
        cachingEnabled = true)
class ListItem(@PrimaryKey(autoincrement = true) var id: Int? = null,
               @ForeignKey(stubbedRelationship = true) var parentList: List? = null,
               var name: String? = null) {

    constructor(parentList: List?, name: String?) : this(null, parentList, name)
}