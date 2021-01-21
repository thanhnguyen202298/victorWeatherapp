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
import com.victorthanh.weather.domain.model.City
import kotlinx.android.synthetic.main.item_text.view.*

class TextAdapter(val activity: BaseActivity, listener: OnAdapterListener<City>) :
    BaseRecyclerAdapter<City>(activity, listener) {


    override fun createView(
        context: Context?,
        viewGroup: ViewGroup?,
        viewType: Int
    ): BaseViewHolder {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_text, viewGroup, false)
        return BaseViewHolder(view)
    }

    override fun bindView(item: City?, position: Int, baseViewHolder: BaseViewHolder?) {
        val v = baseViewHolder?.itemView
        v?.title?.text = item?.name
        v?.setOnClickListener {
            listener.onSelectedItemListener(item, position, v)
        }
    }
}