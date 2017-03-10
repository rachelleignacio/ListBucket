package com.rachelleignacio.listbucket.models;

import com.rachelleignacio.listbucket.db.DbFlowDatabase;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by rachelleignacio on 3/1/17.
 */
@Table(database = DbFlowDatabase.class,
        allFields = true,
        orderedCursorLookUp = true,
        assignDefaultValuesFromCursor = false,
        cachingEnabled = true)
public class List extends BaseModel implements Serializable {

    @PrimaryKey(autoincrement = true)
    private int id;

    private String name;

    public List() {}

    public List(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    // NOTE: this should never get called. Needed by DbFlow because id is a private field
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
