package com.rachelleignacio.listbucket.util;

import com.rachelleignacio.listbucket.models.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rachelleignacio on 3/3/17.
 */

public class MockDataUtil {
    public static List<com.rachelleignacio.listbucket.models.List> getListBucket() {
        ArrayList<com.rachelleignacio.listbucket.models.List> mockList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockList.add(new com.rachelleignacio.listbucket.models.List("List #"+i));
        }
        return mockList;
    }

    public static List<ListItem> getListItems() {
        ArrayList<com.rachelleignacio.listbucket.models.ListItem> mockListItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockListItems.add(new com.rachelleignacio.listbucket.models.ListItem(new com.rachelleignacio.listbucket.models.List("List #" + i), "List Item #"+i));
        }
        return mockListItems;
    }
}
