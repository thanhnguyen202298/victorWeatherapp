package com.victorthanh.utilslib.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.ParseException
import java.util.*
import java.util.concurrent.TimeUnit

fun withSuffix(count: Long): String? {
    if (count < 1000) return "" + count
    val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
    return String.format(
        "%.1f%c",
        count / Math.pow(1000.0, exp.toDouble()),
        "kMGTPE"[exp - 1]
    )
}

fun withSuffixNotUnit(count: Long): String? {
    if (count < 1000) return "" + count
    val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
    return String.format(
        "%.1f",
        count / Math.pow(1000.0, exp.toDouble())
    )
}

fun appendZero(text: String, lenght: Int): String {
    var textTemp = text
    if (TextUtils.isEmpty(textTemp))
        return ""
    if (textTemp.length == lenght)
        return textTemp
    for (i in textTemp.length until lenght) {
        textTemp = "0$textTemp"

    }
    return textTemp
}


fun getBitmap(resourceId: Int, context: Context): Bitmap? {
    val vectorDrawable = ContextCompat.getDrawable(context, resourceId) as? VectorDrawable
        ?: return null
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
}

private val hexArray =
    charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'E', 'F')

fun bytesToHex(bytes: ByteArray): String {
    val hexChars = CharArray(bytes.size * 2)
    for (j in bytes.indices) {
        val v = (bytes[j].toInt() and 0xFF)

        hexChars[j * 2] = hexArray[v ushr 4]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}

private const val HEX_STRING = "0123456789ABCDEF"
private val HEX_CHARS_ARRAY = HEX_STRING.toCharArray()
fun ByteArray.toHex(): String {
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS_ARRAY[firstIndex])
        result.append(HEX_CHARS_ARRAY[secondIndex])
    }


    return result.toString()
}

fun String.hexStringToByteArray(): ByteArray {

    val b = ByteArray(this.length / 2)
    for (i in b.indices) {
        val index = i * 2
        val v = Integer.parseInt(this.substring(index, index + 2), 16)
        b[i] = v.toByte()
    }
    return b
}

fun Int.toHex(): String = Integer.toHexString(this).toUpperCase(Locale.getDefault())

fun String.formatTime(millis: Long): String {
    return String.format(
        "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(
                millis
            )
        ),
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(millis)
        )
    )
}

fun String.formatTimeComprehension(millis: Long): String {
    if (TimeUnit.MILLISECONDS.toHours(millis) > 0) {
        return String.format(
            "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    millis
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )
    }

    return String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(
                millis
            )
        ),
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(
                millis
            )
        )
    )
}


fun String.formatMillisecond(time: String): Long {
    try {
        val array = time.split(":").toTypedArray()
        if (!array.isNullOrEmpty() && array.size == 3) {
            var hour = array[0]
            var min = array[1]
            var sec = array[2]
            return hour.toLong() * 3600000 + min.toLong() * 60000 + sec.toLong() * 1000
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0
}

fun hexToBytes(s: String): ByteArray {
    val len = s.length
    val data = ByteArray(len / 2)

    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
        i += 2
    }
    return data
}

//
//fun isMyServiceRunning(mContext: Context, serviceClass: Class<*>): Boolean {
//    val manager = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//    val runningServiceInfoList = manager.getRunningServices(Integer.MAX_VALUE)
//    for (service in runningServiceInfoList) {
//        if (serviceClass.name == service.service.className) {
//            return true
//        }
//    }
//    return false
//}

fun isValidEmail(target: CharSequence): Boolean =
    !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()

fun isValidPassword(target: CharSequence): Boolean {
    val pattern = "^(?=.*[0-9A-Z,@#\$%^&+=])(?=.*[a-z])(?=\\S+\$).{8,}\$" +
            "|^(?=.*[a-zA-Z,@#\$%^&+=])(?=.*[0-9])(?=\\S+\$).{8,}\$" +
            "|^(?=.*[0-9a-z,@#\$%^&+=])(?=.*[A-Z])(?=\\S+\$).{8,}\$" +
            "|^(?=.*[0-9A-Za-z])(?=.*[,@#\$%^&+=])(?=\\S+\$).{8,}\$"
    return target.matches(Regex(pattern))
}

fun isValidMacAddress(target: CharSequence): Boolean {
    val pattern = "([\\da-fA-F]{2}(?::|\$)){6}"
    return target.matches(Regex(pattern))
}

fun isValidText(target: CharSequence): Boolean = !TextUtils.isEmpty(target)

fun isValidFolderName(target: CharSequence): Boolean {
    val pattern = "^[^\\\\/?%*:|\"<>.]+\$"
    return target.matches(Regex(pattern))
}

fun hideKeyboard(mContext: Activity) {
    val view = mContext.currentFocus
    if (view != null) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun delayExecute(delayTimeInMillisecond: Int): Observable<Long> {
    return Observable.just(0L)
        .delay(delayTimeInMillisecond.toLong(), TimeUnit.MILLISECONDS)
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
}

fun delayExecuteBackground(delayTimeInMillisecond: Int): Observable<Long> {
    return Observable.just(0L)
        .delay(delayTimeInMillisecond.toLong(), TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

