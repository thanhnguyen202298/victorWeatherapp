package com.victorthanh.utilslib.domain.model.openweather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherInfo {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("timezone")
    @Expose
    var timezone: String? = null

    @SerializedName("timezone_offset")
    @Expose
    var timezoneOffset: Int? = null

    @SerializedName("current")
    @Expose
    var current: Current? = null

    @SerializedName("daily")
    @Expose
    var daily: ArrayList<Daily>? = null
}