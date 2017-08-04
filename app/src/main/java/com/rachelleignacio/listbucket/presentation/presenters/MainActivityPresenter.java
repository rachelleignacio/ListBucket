package com.rachelleignacio.listbucket.presentation.presenters;

import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public interface MainActivityPresenter {

    interface View {
        void showListBucket();
        void showList(List list);
    }
}
