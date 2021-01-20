package com.victorthanh.utilslib.utils

import java.io.File
import java.io.InputStream

// Created by Nguyen Trung Thanh on 11/17/2020.
// Copyright (c) 2020 FUVI jsc. All rights reserved.
fun writeFile(filepath: String, inputStream: InputStream) {

    val len = inputStream.available()
    val buffer = ByteArray(len)
    inputStream.read(buffer)

    val file = File(filepath)
    val outstream = file.outputStream()
    outstream.write(buffer)
    outstream.flush()
    outstream.close()
}

fun getfileNameById(id: Int): String {
    return when (id) {
//        R.raw.cognitivevideographic -> "cognitivevideographic.html"
//        R.raw.fabric -> "fabric.js"
//        R.raw.fontfaceobserver -> "fontfaceobserver.js"
//        R.raw.googlefonts -> "googlefonts.css"
//        R.raw.index -> "index.js"
//        R.raw.webfont -> "webfont.js"
        else -> ""
    }
}