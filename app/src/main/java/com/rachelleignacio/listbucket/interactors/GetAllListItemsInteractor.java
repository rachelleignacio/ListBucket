package com.rachelleignacio.listbucket.interactors;

import com.rachelleignacio.listbucket.interactors.base.Interactor;
import com.rachelleignacio.listbucket.models.ListItem;

import java.util.List;

/**
 * Created by rachelleignacio on 3/9/17.
 */

public interface GetAllListItemsInteractor extends Interactor {
    interface Callback {
        void onListItemsRetrieved(List<ListItem> items);
    }
}
