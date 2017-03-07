package com.rachelleignacio.listbucket.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.fragments.CreateListDialogFragment;
import com.rachelleignacio.listbucket.fragments.ListItemsFragment;
import com.rachelleignacio.listbucket.fragments.MainListBucketFragment;
import com.rachelleignacio.listbucket.interactors.CreateListInteractor;

public class MainActivity extends AppCompatActivity implements CreateListInteractor.Callback {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {
            displayLists();
        }
    }

    private void onAddButtonClicked() {
        CreateListDialogFragment.newInstance(this)
                .show(getSupportFragmentManager(), CreateListDialogFragment.TAG);
    }

    @Override
    public void onListCreated() {
        displayLists();
    }

    private void displayLists() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, MainListBucketFragment.newInstance())
                .addToBackStack("MainActivityFragmentStack")
                .commit();
    }

    public void displayListItems(int listId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, ListItemsFragment.newInstance(listId))
                .addToBackStack("MainActivityFragmentStack")
                .commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            moveTaskToBack(false);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
