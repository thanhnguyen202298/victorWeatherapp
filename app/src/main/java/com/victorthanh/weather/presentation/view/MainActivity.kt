package com.victorthanh.weather.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
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
import com.victorthanh.weather.domain.model.City
import com.victorthanh.weather.presentation.adapter.TextAdapter
import com.victorthanh.weather.presentation.adapter.WeatherAdapter
import com.victorthanh.weather.presentation.viewmodel.WeatherViewModel
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import us.fuvi.utils_sdk.data.DataHelper

class MainActivity : BaseActivity() {
    private var weatherinfoAda: WeatherAdapter? = null
    private var savedCityAda: TextAdapter? = null
    private lateinit var viewModel: WeatherViewModel
    private var listCitySeark: List<Result>? = ArrayList()
    private var weatherInfo: WeatherInfo = WeatherInfo()
    private var realmInstance: Realm? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    override fun setupView() {
        goneView(listcity)
        weatherinfoAda = WeatherAdapter(this, object : OnAdapterListener<Daily> {})
        savedCityAda = TextAdapter(this, object : OnAdapterListener<City> {
            override fun onSelectedItemListener(model: City, index: Int, view: View?) {
                cityseark.setText(model.name)
                goneView(listcity)
            }
        })
        realmInstance = DataHelper.realmManager?.realmInstance()

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        viewModel.appMessage.observe(this, Observer {
            showSnackBar(it.message,blanktop)
        })
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
            layoutManager =
                WrapGridLayoutManager(this@MainActivity, 1, GridLayoutManager.VERTICAL, false)
        }

        listcity.apply {
            adapter = savedCityAda
            layoutManager =
                WrapGridLayoutManager(this@MainActivity, 1, GridLayoutManager.VERTICAL, false)
        }

        textEventCity()
    }

    private fun textEventCity() {

        cityseark?.setOnKeyListener { v, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    hideKeyboard(v)
                    goneView(listcity)
                    val textserk = cityseark.text.toString()
                    if (textserk.isNotEmpty()) {
                        onSearkBtn(v)
                        if (findCitySaved(textserk)?.size ?: 0 <1)
                            realmInstance?.executeTransaction {
                                it.createObject(City::class.java, textserk)
                            }
                    }
                }
            }
            false
        }
        cityseark.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty()) {
                val listSavedcity = findCitySaved(text.toString())
                updateSearkListCity(listSavedcity)
            }
            else updateSearkListCity(getAllCitySaved())
        }
    }

    private fun findCitySaved(text: String): RealmResults<City>? {
        return realmInstance?.where(City::class.java)
            ?.contains("name", text.toString())?.findAll()
    }
    private fun getAllCitySaved(): RealmResults<City>? {
        return realmInstance?.where(City::class.java)?.findAll()
    }

    private fun updateSearkListCity(list : RealmResults<City>?){
        if (list?.count() ?: 0 > 0) {
            visibleView(listcity)
            savedCityAda?.list = list
            savedCityAda?.notifyDataSetChanged()
        }else {
            savedCityAda?.list= ArrayList()
            savedCityAda?.notifyDataSetChanged()
        }
    }

    //region event on Views
    fun onSearkBtn(v: View) {
        hideKeyboard(cityseark)
        goneView(listcity)
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