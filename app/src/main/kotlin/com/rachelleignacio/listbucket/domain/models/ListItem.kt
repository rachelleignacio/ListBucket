package com.rachelleignacio.listbucket.domain.models

import com.rachelleignacio.listbucket.db.DbFlowDatabase
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

/**
 * Created by rachelleignacio on 8/4/17.
 */
@Table(database = DbFlowDatabase::class,
        allFields =  true,
        orderedCursorLookUp = true,
        assignDefaultValuesFromCursor = false,
        cachingEnabled = true)
class ListItem() : BaseModel() {

    @PrimaryKey(autoincrement = true)
    private var id: Int = 0

    @ForeignKey(stubbedRelationship = true)
    private lateinit var parentList: List

    private lateinit var name: String

    constructor(parent: List, name: String) : this() {
        this.parentList = parent
        this.name = name
    }

    fun getId(): Int {
        return id
    }

    // NOTE: this should never get called. Needed by DbFlow because id is a private field
    fun setId(id: Int) {
        this.id = id
    }

    fun getParentList(): List {
        return parentList
    }

    // NOTE: this should never get called. Needed by DbFlow because parentList is a private field
    fun setParentList(parent: List) {
        this.parentList = parent
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }
}