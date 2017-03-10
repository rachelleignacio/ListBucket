package com.rachelleignacio.listbucket.interactors;

import com.rachelleignacio.listbucket.interactors.base.Interactor;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public interface AddListItemInteractor extends Interactor {
    interface  Callback {
        void onListItemAdded();
    }
}
