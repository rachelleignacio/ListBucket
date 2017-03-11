package com.rachelleignacio.listbucket.domain.interactors;

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor;
import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public interface GetListBucketInteractor extends Interactor {
    interface Callback {
        void onListsRetrieved(java.util.List<List> lists);
    }
}
