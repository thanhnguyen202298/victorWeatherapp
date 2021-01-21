package com.victorthanh.weather.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.victorthanh.utilslib.domain.model.opencage.CityInfo
import com.victorthanh.utilslib.domain.model.openweather.WeatherInfo
import com.victorthanh.utilslib.functional.ErrorCallBack
import com.victorthanh.utilslib.presentation.base.BaseViewModel
import com.victorthanh.weather.App
import com.victorthanh.weather.domain.usecase.WeatherUsecase

class WeatherViewModel(application: Application) : BaseViewModel(application) {

    val weatherDetail: MutableLiveData<WeatherInfo> = MutableLiveData()
    val cityDetail: MutableLiveData<CityInfo> = MutableLiveData()

    private var weatherUsecase: WeatherUsecase

    val errorCallBack: ErrorCallBack = {
        appMessage.setCloudErrorMessage(it)
    }

    init {
        weatherUsecase = WeatherUsecase(compositeDisposable)
    }

    fun loadCityData(cityname: String) {
        weatherUsecase.loadDataCity(cityname, {
            cityDetail.value = it
        }, errorCallBack)
    }

    fun loadCityData(lat: String, lon: String, exlude: String) {
        weatherUsecase.loadWeatherByLatln(lat, lon, exlude, {
            weatherDetail.value = it
        }, errorCallBack)
    }
}