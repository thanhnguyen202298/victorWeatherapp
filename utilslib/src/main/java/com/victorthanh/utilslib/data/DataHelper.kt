package us.fuvi.utils_sdk.data

import android.content.Context
import com.victorthanh.utilslib.data.preference.Preferences
import com.victorthanh.utilslib.data.preference.PreferencesImp
import com.victorthanh.utilslib.data.local.RealmManager
import com.victorthanh.utilslib.data.remote.NetworkService
import com.victorthanh.utilslib.utils.helper.KeyStoreCryptorHelper


class DataHelper(context: Context) {
    companion object {
        var preferencesImp: PreferencesImp? = null
        var realmManager: RealmManager? = null
        var keyStoreCryptorHelper: KeyStoreCryptorHelper? = null
    }


    init {
        preferencesImp = Preferences(context)
        keyStoreCryptorHelper =
            KeyStoreCryptorHelper(context)
        realmManager = RealmManager(
            context,
            preferencesImp!!,
            keyStoreCryptorHelper!!
        )
        NetworkService.init(preferencesImp)
    }
}