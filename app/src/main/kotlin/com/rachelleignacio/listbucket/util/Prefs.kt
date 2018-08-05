package com.rachelleignacio.listbucket.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Created by rachelleignacio on 9/13/17.
 */
object Prefs {
    private const val PREFS_FILENAME = "com.rachelleignacio.listbucket.prefs"

    fun get(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Value is of a type not yet implemented")
        }
    }

    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when(T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            java.lang.Integer::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            java.lang.Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            java.lang.Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            java.lang.Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented for type " + T::class)
        }
    }
}