package com.rachelleignacio.listbucket.interactors;

import com.rachelleignacio.listbucket.interactors.base.Interactor;

/**
 * Created by rachelleignacio on 3/10/17.
 */

public interface DeleteListItemInteractor extends Interactor {
    interface Callback {
        void onListItemDeleted();
    }
}
