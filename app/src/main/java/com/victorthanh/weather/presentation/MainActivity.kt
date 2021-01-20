package com.victorthanh.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victorthanh.utilslib.presentation.base.BaseActivity
import com.victorthanh.utilslib.domain.model.message.AppMessage
import com.victorthanh.weather.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun restartApp() {

    }

    override fun getActivity(screenSize: Int): AppCompatActivity {
        TODO("Not yet implemented")
    }

    override fun setupView() {

    }

    override fun onEvent(message: AppMessage, bundle: Bundle) {

    }
}