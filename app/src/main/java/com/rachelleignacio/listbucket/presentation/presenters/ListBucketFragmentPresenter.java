package com.rachelleignacio.listbucket.presentation.presenters;

import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public interface ListBucketFragmentPresenter {

    interface View {
        void showLists(java.util.List<List> lists);
    }

    void getLists();
}
