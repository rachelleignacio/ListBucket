package com.rachelleignacio.listbucket.domain.interactors;

import com.rachelleignacio.listbucket.domain.interactors.base.Interactor;
import com.rachelleignacio.listbucket.domain.models.ListItem;

import java.util.List;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public interface GetAllListItemsInteractor extends Interactor {
    interface Callback {
        void onListItemsRetrieved(List<ListItem> items);
    }
}
