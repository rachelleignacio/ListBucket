package com.rachelleignacio.listbucket.domain.interactors;

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor;

/**
 * Created by rachelleignacio on 4/18/17.
 */

public interface RenameListInteractor extends Interactor {
    interface Callback {
        void onListRenamed();
    }
}
