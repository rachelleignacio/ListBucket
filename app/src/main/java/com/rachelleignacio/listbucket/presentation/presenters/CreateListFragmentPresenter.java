package com.rachelleignacio.listbucket.presentation.presenters;

/**
 * Created by rachelleignacio on 3/31/17.
 */

public interface CreateListFragmentPresenter {
    interface View {
        void onListCreated();
    }

    void createList(String listName);
}
