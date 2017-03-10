package com.rachelleignacio.listbucket.interactors;

import com.rachelleignacio.listbucket.interactors.base.Interactor;
import com.rachelleignacio.listbucket.models.List;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public interface GetListBucketInteractor extends Interactor {
    interface Callback {
        void onListsRetrieved(java.util.List<List> lists);
    }
}
