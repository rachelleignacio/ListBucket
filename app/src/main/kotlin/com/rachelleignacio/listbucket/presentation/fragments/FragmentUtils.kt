package com.rachelleignacio.listbucket.presentation.fragments

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.rachelleignacio.listbucket.R

fun initRecyclerView(activity: Activity): RecyclerView =
        activity.findViewById<RecyclerView>(R.id.recycler_view_items)
                .apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity)
                    adapter = null
                }