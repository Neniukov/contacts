package com.example.contacts.extensions

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadCircleImage(url: String?, @DrawableRes placeholderRes: Int? = null) {
    if (this.context.isNotAvailable()) return
    Glide.with(this.context)
        .asBitmap()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .load(url)
        .run { placeholderRes?.let { placeholder(it) } ?: this }
        .circleCrop()
        .into(this)
}

fun View.visibleIfOrGone(predicate: () -> Boolean) {
    if (predicate()) visible() else gone()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}