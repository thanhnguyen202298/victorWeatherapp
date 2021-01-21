package com.victorthanh.weather.presentation.adapter

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import com.victorthanh.utilslib.Constant.IconWeatherLinkPrefix
import com.victorthanh.utilslib.domain.model.openweather.Daily
import com.victorthanh.utilslib.domain.model.openweather.WeatherInfo
import com.victorthanh.utilslib.presentation.base.BaseActivity
import com.victorthanh.utilslib.presentation.base.adapter.BaseRecyclerAdapter
import com.victorthanh.utilslib.presentation.base.event.OnAdapterListener
import com.victorthanh.utilslib.utils.loadImage
import com.victorthanh.utilslib.utils.loadImageBitmap
import com.victorthanh.weather.R
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherAdapter(val activity: BaseActivity, val listenr: OnAdapterListener<Daily>) : BaseRecyclerAdapter<Daily>(activity) {


    override fun createView(context: Context?, viewGroup: ViewGroup?, viewType: Int): BaseViewHolder {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_weather, viewGroup, false)
        return BaseViewHolder(view)
    }

    override fun bindView(item: Daily?, position: Int, baseViewHolder: BaseViewHolder?) {
        val v = baseViewHolder?.itemView
        if (item?.weather?.size ?: 0 > 0)
        {
            val url = "$IconWeatherLinkPrefix/${item?.weather?.get(0)?.icon ?: return}@2x.png"
            loadImageBitmap(activity, url, v?.img_weather!!)
        }

        v?.date?.text = item?.date
        v?.temperature?.text = "${item?.temp?.day}°C"
        v?.presure?.text = "${item?.pressure}"
        v?.humidity?.text = "${item?.humidity}%"
        v?.desc?.text = "${item?.weather?.get(0)?.description}"
    }
}