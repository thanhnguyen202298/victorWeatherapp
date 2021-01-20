package com.victorthanh.weather.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Status {
    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}