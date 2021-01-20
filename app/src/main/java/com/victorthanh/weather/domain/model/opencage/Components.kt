package com.victorthanh.weather.domain.model.opencage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Components {
    @SerializedName("ISO_3166-1_alpha-2")
    @Expose
    var iSO31661Alpha2: String? = null

    @SerializedName("ISO_3166-1_alpha-3")
    @Expose
    var iSO31661Alpha3: String? = null

    @SerializedName("_category")
    @Expose
    var category: String? = null

    @SerializedName("_type")
    @Expose
    var type: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("city_district")
    @Expose
    var cityDistrict: String? = null

    @SerializedName("continent")
    @Expose
    var continent: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("country_code")
    @Expose
    var countryCode: String? = null

    @SerializedName("hamlet")
    @Expose
    var hamlet: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("state_code")
    @Expose
    var stateCode: String? = null

    @SerializedName("county")
    @Expose
    var county: String? = null

    @SerializedName("postcode")
    @Expose
    var postcode: String? = null

    @SerializedName("quarter")
    @Expose
    var quarter: String? = null

    @SerializedName("town")
    @Expose
    var town: String? = null

    @SerializedName("residential")
    @Expose
    var residential: String? = null

    @SerializedName("state_district")
    @Expose
    var stateDistrict: String? = null

    @SerializedName("village")
    @Expose
    var village: String? = null

    @SerializedName("municipality")
    @Expose
    var municipality: String? = null

    @SerializedName("neighbourhood")
    @Expose
    var neighbourhood: String? = null

    @SerializedName("road")
    @Expose
    var road: String? = null

    @SerializedName("road_type")
    @Expose
    var roadType: String? = null

    @SerializedName("region")
    @Expose
    var region: String? = null
}