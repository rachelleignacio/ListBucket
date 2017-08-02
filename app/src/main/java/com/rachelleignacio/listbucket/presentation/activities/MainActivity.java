package com.rachelleignacio.listbucket.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.rachelleignacio.listbucket.BuildConfig;
import com.rachelleignacio.listbucket.R;
import com.rachelleignacio.listbucket.db.DbInteractor;
import com.rachelleignacio.listbucket.domain.executor.impl.MainThreadImpl;
import com.rachelleignacio.listbucket.domain.executor.impl.ThreadExecutor;
import com.rachelleignacio.listbucket.presentation.fragments.ListItemsFragment;
import com.rachelleignacio.listbucket.presentation.fragments.MainListBucketFragment;
import com.rachelleignacio.listbucket.domain.models.List;
import com.rachelleignacio.listbucket.presentation.presenters.MainActivityPresenter;
import com.rachelleignacio.listbucket.presentation.presenters.impl.MainActivityPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private MainActivityPresenter mainActivityPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainActivityPresenter = new MainActivityPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), DbInteractor.getInstance(), this);

        if (savedInstanceState == null) {
            displayLists();
        }
    }

    private void displayLists() {
        showFabAddButton();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, MainListBucketFragment.newInstance())
                .addToBackStack("MainActivityFragmentStack")
                .commit();
    }

    public void displayListItems(List listToDisplay) {
        hideFabAddButton();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, ListItemsFragment.newInstance(listToDisplay))
                .addToBackStack("MainActivityFragmentStack")
                .commit();
    }

    @Override
    public void showLists() {
        displayLists();
    }

    @Override
    public void onClickCreateList() {
        mainActivityPresenter.showCreateListDialog(getSupportFragmentManager());
    }

    @Override
    public void onListSwipedToDelete(List listToDelete) {
        mainActivityPresenter.deleteListFromBucket(listToDelete);
    }

    @Override
    public void onClickRenameList(List listToRename) {
        mainActivityPresenter.showRenameListDialog(getSupportFragmentManager(), listToRename);
    }


    private void showFabAddButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCreateList();
            }
        });
    }

    private void hideFabAddButton() {
        findViewById(R.id.fab).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            moveTaskToBack(false);
        } else {
            showFabAddButton();
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
