package com.victorthanh.utilslib.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Geometry {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
}