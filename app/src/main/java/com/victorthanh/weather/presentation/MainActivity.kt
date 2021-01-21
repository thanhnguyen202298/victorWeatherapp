package com.victorthanh.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victorthanh.utilslib.Constant.ExcludeWeather
import com.victorthanh.utilslib.presentation.base.BaseActivity
import com.victorthanh.utilslib.domain.model.message.AppMessage
import com.victorthanh.utilslib.domain.model.opencage.CityInfo
import com.victorthanh.utilslib.domain.model.opencage.Result
import com.victorthanh.utilslib.domain.model.openweather.Daily
import com.victorthanh.utilslib.domain.model.openweather.WeatherInfo
import com.victorthanh.utilslib.presentation.base.adapter.WrapGridLayoutManager
import com.victorthanh.utilslib.presentation.base.event.OnAdapterListener
import com.victorthanh.weather.R
import com.victorthanh.weather.presentation.adapter.WeatherAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var weatherinfoAda: WeatherAdapter? = null
    private lateinit var viewModel: WeatherViewModel
    private var listCitySeark: List<Result>? = ArrayList()
    private var weatherInfo: WeatherInfo = WeatherInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    override fun setupView() {
        weatherinfoAda = WeatherAdapter(this, object : OnAdapterListener<Daily> {})
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        viewModel.cityDetail.observe(this, Observer {
            listCitySeark = it.results
            getWeatherFromResults(it)
        })

        viewModel.weatherDetail.observe(this, Observer {
            weatherInfo = it
            weatherinfoAda?.list = weatherInfo.daily
            weatherinfoAda?.notifyDataSetChanged()
        })

        listForecast.apply {
            adapter = weatherinfoAda
            layoutManager = WrapGridLayoutManager(this@MainActivity,1, GridLayoutManager.VERTICAL,false)
        }

    }

    //region event on Views
    fun onSearkBtn(v:View){
        val city = cityseark.text.toString()
        viewModel.loadCityData(city)
    }
    //endregion

    private fun callOneWeather(city: Result) {
        val lat = city.geometry?.lat ?: return
        val lon = city.geometry?.lng ?: return

        viewModel.loadCityData(lat.toString(), lon.toString(), ExcludeWeather.minutely.key)
    }

    private fun getWeatherFromResults(cityInfo: CityInfo) {
        if (cityInfo.results?.size ?: 0 > 0) {
            val city = cityInfo.results?.get(0) ?: return
            callOneWeather(city)
        }
    }

    override fun onEvent(message: AppMessage, bundle: Bundle) {

    }

    override fun restartApp() {

    }

    override fun getActivity(screenSize: Int): AppCompatActivity = this

}