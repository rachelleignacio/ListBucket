package com.rachelleignacio.listbucket.models;

import java.io.Serializable;

/**
 * Created by rachelleignacio on 3/1/17.
 */

public class List implements Serializable {
    private int id;
    private String name;

    public List(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

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
