package com.victorthanh.utilslib.presentation.component

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.victorthanh.utilslib.utils.helper.ConnectionHelper

class NetworkComponent(
    private val m_context: Context,
    private val m_listener: ConnectionHelper.ShowConnectionListener
) :
    LifecycleObserver {
    private var mConnectionHelper: ConnectionHelper? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        mConnectionHelper = ConnectionHelper(m_context, m_listener)
        mConnectionHelper!!.registerBroadcastConnection()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (mConnectionHelper == null) return
        mConnectionHelper!!.unregisterBroadcastConnection(m_context)
        mConnectionHelper = null
    }

}