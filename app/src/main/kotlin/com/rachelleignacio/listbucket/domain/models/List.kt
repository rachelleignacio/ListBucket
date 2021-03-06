package com.rachelleignacio.listbucket.domain.models

import com.rachelleignacio.listbucket.db.DbFlowDatabase
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import java.io.Serializable

/**
 * Created by rachelleignacio on 8/4/17.
 */
@Table(database = DbFlowDatabase::class,
        allFields = true,
        orderedCursorLookUp = true,
        assignDefaultValuesFromCursor = false,
        cachingEnabled = true)
class List(@PrimaryKey(autoincrement = true) var id: Int = -1,
           var name: String = "") : Serializable {

    constructor(name: String = "") : this(-1, name)
}