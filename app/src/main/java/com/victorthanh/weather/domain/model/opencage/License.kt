package com.victorthanh.weather.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class License {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
}