package com.rachelleignacio.listbucket.models;

import com.rachelleignacio.listbucket.db.DbFlowDatabase;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by rachelleignacio on 3/1/17.
 */
@Table(database = DbFlowDatabase.class,
        allFields = true,
        orderedCursorLookUp = true,
        assignDefaultValuesFromCursor = false,
        cachingEnabled = true)
public class ListItem extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int id;

    @ForeignKey(stubbedRelationship = true)
    private List parentList;

    private String name;

    public ListItem() {}

    public ListItem(List parent, String name) {
        this.parentList = parent;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    // NOTE: this should never get called. Needed by DbFlow because id is a private field
    public void setId(int id) {
        this.id = id;
    }

    public List getParentList() {
        return parentList;
    }

    // NOTE: this should never get called. Needed by DbFlow because parentList is a private field
    public void setParentList(List parent) {
        this.parentList = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
