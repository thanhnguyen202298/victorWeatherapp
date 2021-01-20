package com.victorthanh.utilslib.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

// Created by Nguyen Trung Thanh on 9/30/2020.
// Copyright (c) 2020 FUVI jsc. All rights reserved.
fun loadImage(context: Activity, url: String, view: ImageView, catex: CatException? = null) {
    try {
        val glide = Glide.with(context)
        glide.load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun loadImageBitmap(
    context: Activity,
    url: String,
    thumbnailVideo: ImageView
) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                thumbnailVideo.setImageBitmap(bitmap)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
}

typealias CatException = (Exception) -> Unit