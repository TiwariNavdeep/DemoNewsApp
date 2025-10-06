package com.example.demosample.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?,placeholder: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)
}


fun ImageView.loadImageCircle(url: String?,placeholder: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .circleCrop()
        .into(this)
}
