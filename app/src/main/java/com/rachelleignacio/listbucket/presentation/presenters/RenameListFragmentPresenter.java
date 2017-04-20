package com.rachelleignacio.listbucket.presentation.presenters;

import com.rachelleignacio.listbucket.domain.models.List;

/**
 * Created by rachelleignacio on 4/19/17.
 */

public interface RenameListFragmentPresenter {
    void renameList(List listToRename, String newListName);
}
