package com.rachelleignacio.listbucket;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by rachelleignacio on 3/6/17.
 */

public class ListBucketApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }
}
