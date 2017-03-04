package com.rachelleignacio.listbucket.util;

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
}
