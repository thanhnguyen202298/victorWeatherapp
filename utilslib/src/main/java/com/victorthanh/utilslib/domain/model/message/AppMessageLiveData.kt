package com.victorthanh.utilslib.domain.model.message

import android.util.Log
import com.victorthanh.utilslib.R
import java.lang.Exception
import java.lang.IllegalArgumentException

class AppMessageLiveData(private val appMessage: AppMessage) : SingleLiveEvent<AppMessage>() {

    val PACKAGE_NAME = "us.fuvi.utils_sdk"
    fun setCloudErrorMessage(error: Throwable) {
        try {
            this.appMessage.provider = AppMessage.PROVIDER_CLOUD
            this.appMessage.type = AppMessage.TYPE_ERROR
            var msg = error.message.toString().replace(PACKAGE_NAME, "")
            this.appMessage.messageId = if (msg.contains("400")) {
                appMessage.code = 400
                12
            } else if (msg.contains("401") || msg.contains("503")) {
                appMessage.code = if(msg.contains("401")) 401 else 503
                45
            } else if (msg.contains("500")) {
                appMessage.code = 500
                23
            } else if (msg.contains("null")) {
                appMessage.code = 0
                12
            } else AppMessage.MESS_ID_UNKNOWN

            this.appMessage.message = msg
            value = appMessage
        } catch (ex: Exception) {
            Log.e("<<<exception", ex.localizedMessage)
        }
    }

    fun setAppErrorMessage(messageId: Int) {
        appMessage.code = AppMessage.MESS_ID_APP
        this.appMessage.type = AppMessage.TYPE_ERROR
        this.appMessage.messageId = messageId
        value = appMessage
    }

    fun setAppSuccessMessage(messageId: Int) {
        appMessage.code = AppMessage.MESS_ID_UNKNOWN
        this.appMessage.type = AppMessage.TYPE_SUCCESS
        this.appMessage.messageId = messageId
        value = appMessage
    }
}