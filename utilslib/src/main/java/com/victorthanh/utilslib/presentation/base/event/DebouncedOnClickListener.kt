package com.victorthanh.utilslib.presentation.base.event

import android.os.SystemClock
import android.view.View
import java.util.*

class DebouncedOnClickListener(val minimumInterval: Long, val onDebouncedListener: ((view: View) -> Unit)?) : View.OnClickListener {

    private var lastClickMap: WeakHashMap<View, Long> = WeakHashMap()

    override fun onClick(clickedView: View?) {
        if (clickedView == null) return
        val previousClickTimestamp = lastClickMap.get(clickedView)
        val currentTimestamp = SystemClock.uptimeMillis()

        lastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp) > minimumInterval) {
            onDebouncedListener?.invoke(clickedView)
        }
    }
}