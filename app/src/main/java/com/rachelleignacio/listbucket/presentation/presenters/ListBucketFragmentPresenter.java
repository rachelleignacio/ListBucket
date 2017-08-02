package com.rachelleignacio.listbucket.presentation.presenters;

import android.support.v4.app.FragmentManager;

import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public interface ListBucketFragmentPresenter {

    interface View {
        void showLists(java.util.List<List> lists);
        void onCLickList(List listToView);
        void onClickCreateList();
        void onListSwipedToDelete(int position);
        void onClickRenameList(List listToRename);
    }

    void getLists();
    void showCreateListDialog(FragmentManager fragmentManager);
    void deleteListFromBucket(int position);
    void showRenameListDialog(FragmentManager fragmentManager, List listToRename);
}
