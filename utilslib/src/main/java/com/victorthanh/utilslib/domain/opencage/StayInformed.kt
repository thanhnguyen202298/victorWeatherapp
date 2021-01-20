package com.victorthanh.utilslib.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StayInformed {
    @SerializedName("blog")
    @Expose
    var blog: String? = null

    @SerializedName("twitter")
    @Expose
    var twitter: String? = null
}