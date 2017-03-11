package com.rachelleignacio.listbucket.domain.interactors;

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public interface AddListItemInteractor extends Interactor {
    interface  Callback {
        void onListItemAdded();
    }
}
