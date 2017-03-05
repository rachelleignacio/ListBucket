package com.rachelleignacio.listbucket.models;

/**
 * Created by rachelleignacio on 3/1/17.
 */

public class ListItem {
    private int id;
    private int parentListId;
    private String name;

    public ListItem(int parentId, String name) {
        this.parentListId = parentId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
