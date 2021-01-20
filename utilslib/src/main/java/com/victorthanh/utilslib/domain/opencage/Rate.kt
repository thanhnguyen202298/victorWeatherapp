package com.victorthanh.utilslib.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Rate {
    @SerializedName("limit")
    @Expose
    var limit: Int? = null

    @SerializedName("remaining")
    @Expose
    var remaining: Int? = null

    @SerializedName("reset")
    @Expose
    var reset: Int? = null
}