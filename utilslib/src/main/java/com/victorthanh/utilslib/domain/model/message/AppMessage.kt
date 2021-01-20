package com.victorthanh.utilslib.domain.model.message

class AppMessage() {

    var type: String =
        TYPE_ERROR

    var messageId: Int =
        MESS_ID_UNKNOWN
 var code: Int =
        MESS_ID_UNKNOWN

    var provider: String = PROVIDER_APP

    var message: String = ""

    var moreInfoLink: String = ""

    companion object {

        const val PROVIDER_CLOUD = "CloudMessage"
        const val PROVIDER_APP = "AppMessage"

        const val TYPE_SUCCESS = "Success"
        const val TYPE_ERROR = "Error"
        const val MESS_ID_UNKNOWN = -1
        const val MESS_ID_APP = 1
    }
}