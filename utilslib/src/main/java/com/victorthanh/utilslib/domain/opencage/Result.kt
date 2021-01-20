package com.victorthanh.utilslib.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Result {
    @SerializedName("bounds")
    @Expose
    var bounds: Bounds? = null

    @SerializedName("components")
    @Expose
    var components: Components? = null

    @SerializedName("confidence")
    @Expose
    var confidence: Int? = null

    @SerializedName("formatted")
    @Expose
    var formatted: String? = null

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null
}