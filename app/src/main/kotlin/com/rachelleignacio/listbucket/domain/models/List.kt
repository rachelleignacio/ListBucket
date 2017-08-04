package com.rachelleignacio.listbucket.domain.models

import com.rachelleignacio.listbucket.db.DbFlowDatabase
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import java.io.Serializable

/**
 * Created by rachelleignacio on 8/4/17.
 */
@Table(database = DbFlowDatabase::class,
        allFields = true,
        orderedCursorLookUp = true,
        assignDefaultValuesFromCursor = false,
        cachingEnabled = true)
class List() : BaseModel(), Serializable {

    @PrimaryKey(autoincrement = true)
    private var id: Int = 0

    private var name: String = ""

    constructor(name: String) : this() {
        this.name = name
    }


    fun getId(): Int {
        return id
    }

    // NOTE: this should never get called. Needed by DbFlow because id is a private field
    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }
}