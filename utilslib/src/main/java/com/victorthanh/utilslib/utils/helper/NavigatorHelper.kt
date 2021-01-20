package com.victorthanh.utilslib.utils.helper

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.victorthanh.utilslib.Constant.*


object NavigatorHelper {

    private val keyDataExtraList = ArrayList<String>()

    init {
        keyDataExtraList.add(
            KEY_EXTRA_DATA_1
        )
        keyDataExtraList.add(
            KEY_EXTRA_DATA_2
        )
        keyDataExtraList.add(
            KEY_EXTRA_DATA_3
        )
        keyDataExtraList.add(
            KEY_EXTRA_DATA_4
        )
        keyDataExtraList.add(
            KEY_EXTRA_DATA_5
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    fun <T> navigateActivityForResult(
        context: Activity,
        fragment: Fragment,
        destination: Class<T>,
        requestCode: Int,
        extraData: List<Any> = ArrayList(),
        vararg intentFlags: Int = intArrayOf()
    ) {
        val intent = Intent(context, destination)

        if (extraData.isNotEmpty() && extraData.size <= 5) {
            extraData.forEachIndexed { index, data ->
                putExtra(
                    intent,
                    keyDataExtraList[index],
                    data
                )
            }
        } else if (extraData.size > 5)
            Log.e("<<out of size", "")

        if (intentFlags.isNotEmpty()) {
            intentFlags.forEach { intent.addFlags(it) }
        }

        fragment.startActivityForResult(
            intent,
            requestCode,
            ActivityOptions.makeSceneTransitionAnimation(context).toBundle()
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    fun <T> navigateActivityForResult(
        context: Activity,
        destination: Class<T>,
        requestCode: Int,
        extraData: List<Any> = ArrayList(),
        vararg intentFlags: Int = intArrayOf()
    ) {
        val intent = Intent(context, destination)

        if (extraData.isNotEmpty() && extraData.size <= 5) {
            extraData.forEachIndexed { index, data ->
                putExtra(
                    intent,
                    keyDataExtraList[index],
                    data
                )
            }
        } else if (extraData.size > 5)
            Log.e("<<out of size", "")
        if (intentFlags.isNotEmpty()) {
            intentFlags.forEach { intent.addFlags(it) }
        }

        context.startActivityForResult(
            intent,
            requestCode,
            ActivityOptions.makeSceneTransitionAnimation(context).toBundle()
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    fun <T> navigateActivity(
        context: Activity,
        destination: Class<T>,
        extraData: List<Any> = ArrayList(),
        vararg intentFlags: Int = intArrayOf()
    ) {
        val intent = Intent(context, destination)
        if (destination.name == "SplashActivity") intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        if (extraData.isNotEmpty() && extraData.size <= 5) {
            extraData.forEachIndexed { index, data ->
                putExtra(
                    intent,
                    keyDataExtraList[index],
                    data
                )
            }
        } else if (extraData.size > 5)
            Log.e("<<out of size", "")
        if (intentFlags.isNotEmpty()) {
            intentFlags.forEach { intent.addFlags(it) }
        }

        context.startActivity(
            intent,
            ActivityOptions.makeSceneTransitionAnimation(context).toBundle()
        )
    }

    @JvmStatic
    fun <T> navigateActivity(
        context: Activity,
        destination: Class<T>,
        extraData: List<Any> = ArrayList(),
        activityOption: ActivityOptions? = null,
        vararg intentFlags: Int = intArrayOf()
    ) {
        val intent = Intent(context, destination)

        if (extraData.isNotEmpty() && extraData.size <= 5) {
            extraData.forEachIndexed { index, data ->
                putExtra(
                    intent,
                    keyDataExtraList[index],
                    data
                )
            }
        } else if (extraData.size > 5)
            Log.e("<<out of size", "")
        if (intentFlags.isNotEmpty()) {
            intentFlags.forEach { intent.addFlags(it) }
        }

        context.startActivity(intent, activityOption?.toBundle())
    }

    @JvmStatic
    fun navigateWebsite(context: Activity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

    private fun putExtra(intent: Intent, key: String, data: Any) {
        when (data) {
            is ArrayList<*> -> intent.putExtra(key, data as ArrayList<Parcelable>)
            is Parcelable -> intent.putExtra(key, data)
            is Int -> intent.putExtra(key, data)
            is Float -> intent.putExtra(key, data)
            is Double -> intent.putExtra(key, data)
            is Boolean -> intent.putExtra(key, data)
            is IntArray -> intent.putExtra(key, data)
            is String -> intent.putExtra(key, data)
            else -> intent.putExtra(key, data as String)
        }
    }

}