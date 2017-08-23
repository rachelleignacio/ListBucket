package com.rachelleignacio.listbucket.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutorImpl;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.fragments.ListItemsFragment;
import com.rachelleignacio.listbucket.presentation.fragments.MainListBucketFragment;
import com.rachelleignacio.listbucket.presentation.presenters.MainActivityPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.MainActivityPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private MainActivityPresenter mainActivityPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainActivityPresenter = new MainActivityPresenterImpl(ThreadExecutorImpl.Companion.getInstance(),
                MainThreadImpl.Companion.getInstance());

        if (savedInstanceState == null) {
            showListBucket();
        }
    }

    @Override
    public void showListBucket() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, MainListBucketFragment.newInstance())
                .addToBackStack("MainActivityFragmentStack")
                .commit();
    }

    @Override
    public void showList(List listToDisplay) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, ListItemsFragment.newInstance(listToDisplay))
                .addToBackStack("MainActivityFragmentStack")
                .commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            moveTaskToBack(false);
        } else {
            setTitle(getString(R.string.app_name));
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
