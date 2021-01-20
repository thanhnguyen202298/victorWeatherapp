package com.victorthanh.utilslib.presentation.base.adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

open class WrapGridLayoutManager(context: Context?, spanCount: Int, orientation: Int, reverseLayout: Boolean) : androidx.recyclerview.widget.GridLayoutManager(context, spanCount, orientation, reverseLayout) {

    /* Use this to avoid bug out of bound index of recycler view */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (ex: IndexOutOfBoundsException) {
            Log.w("Outof bounce","$ex")
        }
    }
//
//    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
//        return 600
//    }

}