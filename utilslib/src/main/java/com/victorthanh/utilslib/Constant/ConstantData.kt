package com.victorthanh.utilslib.Constant

// Created by Nguyen Trung Thanh on 10/2/2020.
// Copyright (c) 2020 FUVI jsc. All rights reserved.
const val emptyData = "--empty-block-semantic--"
const val colorBlueLight = "BLUE"
const val colorYellowLight = "YELLOW"
const val colorOrangeLight = "ORANGE"
const val IconWeatherLinkPrefix = "http://openweathermap.org/img/wn" //iconCode@2x.png

enum class ExcludeWeather(val key:String){
    current("current"),
    minutely("minutely"),
    hourly("hourly"),
    daily("daily"),
    alerts("alerts")
}