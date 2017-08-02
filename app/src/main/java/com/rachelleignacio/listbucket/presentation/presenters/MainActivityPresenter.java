package com.rachelleignacio.listbucket.presentation.presenters;

import android.support.v4.app.FragmentManager;

import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public interface MainActivityPresenter {

    interface View {
        void showListBucket();
        void showList(List list);
//        void showLists();
//        void onClickCreateList();
//        void onListSwipedToDelete(List listToDelete);
//        void onClickRenameList(List listToRename);
    }

//    void showCreateListDialog(FragmentManager fragmentManager);
//    void deleteListFromBucket(List listToDelete);
//    void showRenameListDialog(FragmentManager fragmentManager, List listToRename);
}
