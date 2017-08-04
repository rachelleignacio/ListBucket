package com.rachelleignacio.listbucket.db

import com.raizlabs.android.dbflow.annotation.Database

/**
 * Created by rachelleignacio on 8/4/17.
 */
@Database(name = DbFlowDatabase.NAME,
        version = DbFlowDatabase.VERSION,
        foreignKeyConstraintsEnforced = true)
object DbFlowDatabase {
    const val NAME = "ListBucketDatabase"
    const val VERSION = 1
}