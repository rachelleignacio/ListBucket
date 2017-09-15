package com.rachelleignacio.listbucket

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowLog
import com.raizlabs.android.dbflow.config.FlowManager

/**
 * Created by rachelleignacio on 8/23/17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FlowManager.init(FlowConfig.Builder(this)
                .openDatabasesOnInit(true)
                .build())
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V)
    }
}