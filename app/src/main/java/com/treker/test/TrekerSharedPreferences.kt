package com.treker.test

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Sunmer on 2021-02-04
 */

const val SETTINGS_SP = "settings"
const val TOKEN_SP = "token"

class TrekerSharedPreferences {

    fun getSettingSP(): SharedPreferences {
        return TrekerApplication.context.getSharedPreferences(SETTINGS_SP, Context.MODE_PRIVATE)
    }

    fun getTokenSP(): SharedPreferences {
        return TrekerApplication.context.getSharedPreferences(TOKEN_SP, Context.MODE_PRIVATE)
    }

}