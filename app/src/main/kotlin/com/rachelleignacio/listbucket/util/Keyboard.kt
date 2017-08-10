package com.rachelleignacio.listbucket.util

import android.os.IBinder
import android.view.inputmethod.InputMethodManager

/**
 * Created by rachelleignacio on 8/9/17.
 */
object Keyboard {
    fun show(imm: InputMethodManager) {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hide(imm: InputMethodManager, windowToken: IBinder) {
        imm.hideSoftInputFromInputMethod(windowToken, 0)
    }
}