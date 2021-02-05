package com.treker.test

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Sunmer on 2021-02-04
 */

const val SETTING_SP = "setting"
const val TOKEN_SP = "token"

const val SIGNATURE = "signature"
const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"
const val DEVICE_TOKEN = "device_token"
const val UUID = "uuid"

object TrekerSharedPreferences {

    fun settingSP(): SharedPreferences =
        TrekerApplication.context.getSharedPreferences(SETTING_SP, Context.MODE_PRIVATE)

    fun tokenSP(): SharedPreferences =
        TrekerApplication.context.getSharedPreferences(TOKEN_SP, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun saveToken(signature: String?, accessToken: String?, refreshToken: String?) {
        tokenSP().edit().apply() {
            putString(SIGNATURE, signature)
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
        }.apply()
    }

    /**
     * puts a value for the given [key].
     */
    operator fun SharedPreferences.set(key: String, value: Any?) = when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

    /**
     * finds a preference based on the given [key].
     * [T] is the type of value
     * @param defaultValue optional defaultValue - will take a default defaultValue if it is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T = when (T::class) {
        String::class -> getString(key, defaultValue as? String ?: "") as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

}