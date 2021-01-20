package com.victorthanh.utilslib.functional

// Created by Nguyen Trung Thanh on 9/17/2020.
// Copyright (c) 2020 FUVI jsc. All rights reserved.

interface PlayerStateControl {
    fun changePlayState(isplay: Boolean)
    fun changeProgress(position: Long)
    fun changeMediaSource(uuid: String)
}