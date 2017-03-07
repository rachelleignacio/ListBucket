package com.rachelleignacio.listbucket.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by rachelleignacio on 3/6/17.
 */
@Database(name = DbFlowDatabase.NAME,
        version = DbFlowDatabase.VERSION,
        foreignKeyConstraintsEnforced = true)
public class DbFlowDatabase {
    public static final String NAME = "ListBucketDatabase";
    public static final int VERSION = 1;
}
