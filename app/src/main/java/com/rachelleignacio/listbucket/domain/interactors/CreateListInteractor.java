package com.rachelleignacio.listbucket.domain.interactors;

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor;
import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 3/7/17.
 */

public interface CreateListInteractor extends Interactor {
    interface Callback {
        void onListCreated(List newList);
    }
}
