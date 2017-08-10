package com.rachelleignacio.listbucket.util;

import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by rachelleignacio on 8/9/17.
 */

public class Keyboard {
    private static final Keyboard ourInstance = new Keyboard();

    public static Keyboard getInstance() {
        return ourInstance;
    }

    private Keyboard() {
    }

    public void showKeyboard(InputMethodManager imm) {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void hideKeyboard(InputMethodManager imm, IBinder windowToken) {
        imm.hideSoftInputFromWindow(windowToken, 0);
    }
}
