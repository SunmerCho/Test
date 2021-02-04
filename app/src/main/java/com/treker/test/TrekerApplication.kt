package com.treker.test

import android.app.Application
import android.content.Context

class TrekerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}