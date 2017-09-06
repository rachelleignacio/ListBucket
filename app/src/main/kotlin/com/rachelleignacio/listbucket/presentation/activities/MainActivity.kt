package com.rachelleignacio.listbucket.presentation.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.rachelleignacio.listbucket.R
import com.rachelleignacio.listbucket.domain.models.List
import com.rachelleignacio.listbucket.presentation.fragments.ListItemsFragment
import com.rachelleignacio.listbucket.presentation.fragments.MainListBucketFragment
import com.rachelleignacio.listbucket.presentation.presenters.MainActivityPresenter
import com.rachelleignacio.listbucket.util.Keyboard

/**
 * Created by rachelleignacio on 8/29/17.
 */
class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            showListBucket()
        }
    }

    override fun showListBucket() {
        supportFragmentManager
                .beginTransaction()
                .replace(CONTENT_FRAME_ID, MainListBucketFragment.newInstance())
                .addToBackStack(BACKSTACK_NAME)
                .commit()
    }

    override fun showList(list: List) {
        supportFragmentManager
                .beginTransaction()
                .replace(CONTENT_FRAME_ID, ListItemsFragment.newInstance(list))
                .addToBackStack(BACKSTACK_NAME)
                .commit()
    }

    override fun onStop() {
        Keyboard.hide(this, this.currentFocus)
        super.onStop()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            moveTaskToBack(false)
        } else {
            title = (getString(R.string.app_name))
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflage the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        val id = item!!.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        val BACKSTACK_NAME = "MainActivityFragmentStack"
        val CONTENT_FRAME_ID = R.id.content_frame
    }
}