package com.example.liquidui.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {
    fun load(url: String?, imageView: ImageView) {
        url?.let {
            Glide.with(imageView.context)
                .load(it)
                .into(imageView)
        }
    }
}