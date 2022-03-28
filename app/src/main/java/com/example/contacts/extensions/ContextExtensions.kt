package com.example.contacts.extensions

import android.app.Activity
import android.app.Application
import android.content.Context


fun Context?.isNotAvailable(): Boolean = when (this) {
    null -> true
    is Application -> false
    is Activity -> (this.isDestroyed or this.isFinishing)
    else -> false
}