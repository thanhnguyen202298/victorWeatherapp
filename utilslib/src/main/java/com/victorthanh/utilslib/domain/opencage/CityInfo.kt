package com.victorthanh.utilslib.domain.model.opencage

import android.provider.Telephony.Mms.Rate
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CityInfo {
    @SerializedName("documentation")
    @Expose
    var documentation: String? = null

    @SerializedName("licenses")
    @Expose
    var licenses: List<License>? = null

    @SerializedName("rate")
    @Expose
    var rate: Rate? = null

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

    @SerializedName("status")
    @Expose
    var status: Status? = null

    @SerializedName("stay_informed")
    @Expose
    var stayInformed: StayInformed? = null

    @SerializedName("thanks")
    @Expose
    var thanks: String? = null

    @SerializedName("timestamp")
    @Expose
    var timestamp: Timestamp? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
}