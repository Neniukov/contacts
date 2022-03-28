package com.example.contacts.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri

fun Activity.openUrl(url: String) {
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
        startActivity(this)
    }
}