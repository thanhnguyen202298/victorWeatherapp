package com.victorthanh.weather.domain.usecase

import com.victorthanh.utilslib.data.remote.NetworkService
import com.victorthanh.utilslib.domain.model.opencage.CityInfo
import com.victorthanh.utilslib.domain.model.openweather.WeatherInfo
import com.victorthanh.utilslib.functional.ErrorCallBack
import io.reactivex.disposables.CompositeDisposable
import us.fuvi.utils_sdk.base.BaseUseCase

class WeatherUsecase(private val compositeDisposable: CompositeDisposable) : BaseUseCase() {

    fun loadDataCity(cityname: String, callBack: CityResponse, errorCallBack: ErrorCallBack) {
        compositeDisposable.add(
            NetworkService.getCityInfoByName(cityname).subscribe({
                callBack(it)
            }, {
                errorCallBack(it)
            })
        )
    }

    fun loadWeatherByLatln(
        lat: String,
        lon: String,
        exlude: String,
        callBack: WeatherResponse,
        errorCallBack: ErrorCallBack
    ) {
        compositeDisposable.add(
            NetworkService.getWeatherBylatlon(lat, lon, exlude)
                .subscribe({ callBack(it) }, { errorCallBack(it) })
        )
    }
}
typealias CityResponse = (CityInfo) -> Unit
typealias WeatherResponse = (WeatherInfo) -> Unit