package com.rachelleignacio.listbucket.interactors;

import com.rachelleignacio.listbucket.interactors.base.Interactor;

/**
 * Created by rachelleignacio on 3/7/17.
 */

public interface CreateListInteractor extends Interactor {
    interface Callback {
        void onListCreated();
    }
}
