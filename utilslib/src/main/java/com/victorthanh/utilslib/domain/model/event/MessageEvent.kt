package com.victorthanh.utilslib.domain.model.event

import android.view.View
import com.victorthanh.utilslib.Constant.Constants.EventMessage.INIT_EMPTY_TIME_VIDEO


class MessageEvent {
    var type: String = ""
    var modeVideo: String = ""
    var time: Long? = INIT_EMPTY_TIME_VIDEO
    var videoId: String = ""
    var videoUri: String = ""
    var positionChapter: Int = 0
    var imageThumbnail: String = ""
    var isScrolling: Boolean = false
    var viewCenterCG: View? = null
    var filterScreen: Int = -1
    var indexChapter: Int = -1
    var showSketch: Boolean = false
    var userComprehension: String = ""
    var stateModeVideo: String = ""


    constructor(type: String, modeVideo: String, time: Long) {
        this.type = type
        this.modeVideo = modeVideo
        this.time = time
    }

    constructor(type: String, modeVideo: String, time: Long, indexChapter: Int = -1) {
        this.type = type
        this.modeVideo = modeVideo
        this.time = time
        this.indexChapter = indexChapter
    }

    constructor(type: String, time: Long, imageThumbnail: String) {
        this.type = type
        this.time = time
        this.imageThumbnail = imageThumbnail
    }

    constructor(type: String, videoId: String, videoUri: String) {
        this.type = type
        this.videoId = videoId
        this.videoUri = videoUri
    }

    constructor(type: String, showSketch: Boolean, userComprehension: String) {
        this.type = type
        this.showSketch = showSketch
        this.userComprehension = userComprehension
    }

    constructor(type: String, isScrolling: Boolean) {
        this.type = type
        this.isScrolling = isScrolling
    }

    constructor(type: String, filterScreen: Int) {
        this.type = type
        this.filterScreen = filterScreen
    }

    constructor(type: String, stateModeVideo: String) {
        this.type = type
        this.stateModeVideo = stateModeVideo
    }

    constructor(type: String) {
        this.type = type
    }

    constructor(type: String, positionChapter: Int, view: View? = null) {
        this.type = type
        if (view != null) {
            this.viewCenterCG = view
        }
        this.positionChapter = positionChapter
    }
}