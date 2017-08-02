package com.rachelleignacio.listbucket.domain.interactors;

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor;

/**
 * Created by rachelleignacio on 3/10/17.
 */

public interface DeleteListInteractor extends Interactor {
    interface Callback {
        void onListDeleted(int adapterPosition);
    }
}
