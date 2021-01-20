package com.victorthanh.utilslib.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Timestamp {
    @SerializedName("created_http")
    @Expose
    var createdHttp: String? = null

    @SerializedName("created_unix")
    @Expose
    var createdUnix: Int? = null
}