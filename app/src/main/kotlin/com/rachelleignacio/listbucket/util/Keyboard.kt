package com.rachelleignacio.listbucket.util

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by rachelleignacio on 8/9/17.
 */
object Keyboard {
    fun show(ctx: Context, view: View) {
        view.postDelayed( { getImmFromContext(ctx).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT) }, 100)

    }

    fun hide(ctx: Context, view: View) {
        getImmFromContext(ctx).hideSoftInputFromInputMethod(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun getImmFromContext(ctx: Context): InputMethodManager {
        return ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}