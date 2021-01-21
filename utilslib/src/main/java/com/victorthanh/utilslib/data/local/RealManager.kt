package com.victorthanh.utilslib.data.local

import android.content.Context
import com.victorthanh.utilslib.Constant.ALIAS_KEY_REALM
import com.victorthanh.utilslib.data.preference.PreferenceKey
import com.victorthanh.utilslib.data.preference.PreferencesImp
import com.victorthanh.utilslib.utils.helper.KeyStoreCryptorHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import java.security.SecureRandom

class RealmManager constructor(private val context: Context, private val preferences: PreferencesImp, private val keyStoreCryptorHelper: KeyStoreCryptorHelper)  {

    private lateinit var userRealmConfig: RealmConfiguration
    private val REALM_DATABASE_NAME = "thanhnguyen.realm"


    fun realmInstance(): Realm = Realm.getDefaultInstance()

    fun configRealmDatabase() {
        Realm.init(context)

        /* Security realm database */
        val keyEnCryptRealm = preferences[PreferenceKey.KEY_ENCRYPT_REALM, String::class.java]
        val realmKey = if (keyEnCryptRealm.trim().isEmpty()) {
            /* Init new keyEncryptRealm */
            val key = ByteArray(64)
            SecureRandom().nextBytes(key)

            /* Generate new alias key */
            keyStoreCryptorHelper.genKey(ALIAS_KEY_REALM)
            val encrypted = keyStoreCryptorHelper.encryptData(ALIAS_KEY_REALM, key)

            preferences.put(PreferenceKey.KEY_ENCRYPT_REALM, encrypted)
            key
        }
        else {
            /* Exist keyEncryptRealm */
            var key = keyStoreCryptorHelper.decryptData(ALIAS_KEY_REALM, keyEnCryptRealm)

            /* Prevent Bug In Case: User uninstall app and then reinstall.
             => on some phones Share Pref do not delete all older data (tested on SamSung S8 Android 9, and Xiaomi Mix 2S Android 9)
             => It make realm key will be empty lead to crash app. So in this case, we will need generate a new key
             */
            if (key.isEmpty()) {
                /* Init new keyEncryptRealm */
                key = ByteArray(64)
                SecureRandom().nextBytes(key)

                /* Generate new alias key */
                keyStoreCryptorHelper.genKey(ALIAS_KEY_REALM)
                val encrypted = keyStoreCryptorHelper.encryptData(ALIAS_KEY_REALM, key)

                preferences.put(PreferenceKey.KEY_ENCRYPT_REALM, encrypted)
            }
            key
        }

        userRealmConfig = RealmConfiguration.Builder()
            .name(REALM_DATABASE_NAME)
            .encryptionKey(realmKey)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(userRealmConfig)
    }

    fun deleteRealmDatabase() {
        /* Prefer use Realm.deleteRealm => it will delete all realm database in case of missing encryption key.
         * But if realm is open => it will lead to exception, so we will handle this exception and will manual delete realm database
         * */
        try {
            Realm.deleteRealm(userRealmConfig)
        } catch (ex: Exception) {
            realmInstance().executeTransaction { it.deleteAll() }
        }
    }
}