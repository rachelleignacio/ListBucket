package com.rachelleignacio.listbucket.domain.interactors.base;

/**
 * Created by rachelleignacio on 3/6/17.
 * Interface containing the main method that starts an interactor, making sure the operation is done
 * on a background thread.
 */
public interface Interactor {
    void execute();
}
