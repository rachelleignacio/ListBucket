package com.rachelleignacio.listbucket.executor.impl;

import android.os.Handler;
import android.os.Looper;

import com.rachelleignacio.listbucket.executor.MainThread;

/**
 * Created by rachelleignacio on 3/6/17.
 * This makes sure that the provided runnable will be run on the main UI thread.
 */

public class MainThreadImpl implements MainThread {
    private static MainThread mainThread;
    private Handler handler;

    private MainThreadImpl() {
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    public static MainThread getInstance() {
        if (mainThread == null) {
            mainThread = new MainThreadImpl();
        }
        return mainThread;
    }
}
