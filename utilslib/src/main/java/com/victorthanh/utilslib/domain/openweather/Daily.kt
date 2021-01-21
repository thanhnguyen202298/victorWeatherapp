package com.victorthanh.utilslib.domain.model.openweather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Daily {
    @SerializedName("dt")
    @Expose
    var dt: Long? = null

    var date: String = ""

    @SerializedName("sunrise")
    @Expose
    var sunrise: Int? = null

    @SerializedName("sunset")
    @Expose
    var sunset: Int? = null

    @SerializedName("temp")
    @Expose
    var temp: Temp? = null

    @SerializedName("feels_like")
    @Expose
    var feelsLike: FeelsLike? = null

    @SerializedName("pressure")
    @Expose
    var pressure: Int? = null

    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null

    @SerializedName("dew_point")
    @Expose
    var dewPoint: Double? = null

    @SerializedName("wind_speed")
    @Expose
    var windSpeed: Double? = null

    @SerializedName("wind_deg")
    @Expose
    var windDeg: Int? = null

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    @SerializedName("clouds")
    @Expose
    var clouds: Int? = null

    @SerializedName("pop")
    @Expose
    var pop: Double? = null

    @SerializedName("rain")
    @Expose
    var rain: Double? = null

    @SerializedName("uvi")
    @Expose
    var uvi: Double? = null

    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null


}