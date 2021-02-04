package com.treker.test

import android.widget.Toast
import androidx.annotation.StringRes

fun Toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(TrekerApplication.context, message, duration).show()
}

fun Toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(TrekerApplication.context, resId, duration).show()
}


