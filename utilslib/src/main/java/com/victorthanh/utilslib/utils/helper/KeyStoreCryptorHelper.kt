package com.victorthanh.utilslib.utils.helper

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.annotation.RequiresApi
import com.victorthanh.utilslib.Constant.ALGORITHM_RSA
import com.victorthanh.utilslib.Constant.SECURITY_PROVIDER_ANDROID_KEY_STORE
import com.victorthanh.utilslib.Constant.TRANSFORMATION
import com.victorthanh.utilslib.data.preference.PreferenceKey
import com.victorthanh.utilslib.data.preference.PreferencesImp
import com.victorthanh.utilslib.utils.hexStringToByteArray
import com.victorthanh.utilslib.utils.toHex
import java.math.BigInteger
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal


class KeyStoreCryptorHelper constructor(val context: Context) {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Suppress("DEPRECATION")
    fun genKey(alias: String): KeyPair {
        val generator = KeyPairGenerator.getInstance(
            ALGORITHM_RSA,
            SECURITY_PROVIDER_ANDROID_KEY_STORE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /* Above Android M */
            val spec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA1)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build()
            generator.initialize(spec)

        } else {
            /* Below Android M */
            val start = Calendar.getInstance()
            val end = Calendar.getInstance()
            end.add(Calendar.YEAR, 30)
            val spec = KeyPairGeneratorSpec.Builder(context)
                .setAlias(alias)
                .setSubject(X500Principal("CN=Sample Name, O=Android Authority"))
                .setSerialNumber(BigInteger.ONE)
                .setStartDate(start.time)
                .setEndDate(end.time)
                .build()
            generator.initialize(spec)

        }
        return generator.generateKeyPair()
    }

    fun doCryptByKey(isEndCode: Boolean, data: ByteArray, preferences: PreferencesImp): ByteArray {
        var keyPub = preferences[PreferenceKey.KEY_PUBLIC_ENCRYPT, String::class.java]
        if (keyPub.isEmpty()) {
            val generator = KeyPairGenerator.getInstance(ALGORITHM_RSA)
            generator.initialize(2048)
            val keypair = generator.genKeyPair()

            keyPub = keypair.public.encoded.toHex()
            preferences.put(PreferenceKey.KEY_PUBLIC_ENCRYPT, keyPub)

            val keyPrivate = keypair.private.encoded.toHex()
            preferences.put(PreferenceKey.KEY_PRIVATE_ENCRYPT, keyPrivate)
        }

        val publicKey = getPublic(keyPub.hexStringToByteArray())

        val cipher = Cipher.getInstance(TRANSFORMATION)
        return if (isEndCode) {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            cipher.doFinal(data)
        } else {
            val keyPrivate = preferences.get(PreferenceKey.KEY_PRIVATE_ENCRYPT, String::class.java)
            val privateKey = getPrivate(keyPrivate.hexStringToByteArray())
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            cipher.doFinal(data)
        }
    }

    @Throws(java.lang.Exception::class)
    fun getPublic(keyBytes: ByteArray?): PublicKey? {
        val spec = X509EncodedKeySpec(keyBytes)
        val kf = KeyFactory.getInstance(ALGORITHM_RSA)
        return kf.generatePublic(spec)
    }

    @Throws(java.lang.Exception::class)
    fun getPrivate(keyBytes: ByteArray?): PrivateKey? {
        val spec = PKCS8EncodedKeySpec(keyBytes)
        val kf = KeyFactory.getInstance(ALGORITHM_RSA)
        return kf.generatePrivate(spec)
    }

    fun initCipher(alias: String): Cipher? {
        return try {
            val keyStore = KeyStore.getInstance(SECURITY_PROVIDER_ANDROID_KEY_STORE)
            keyStore.load(null)
            val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val publicKey: PublicKey = privateKeyEntry.certificate.publicKey
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            cipher
        } catch (e: Exception) {
            Log.e("<<Error","ENCRYPTION ERROR: $e")
            null
        }
    }

    fun encryptData(alias: String, data: ByteArray): String {
        return try {
            val keyStore = KeyStore.getInstance(SECURITY_PROVIDER_ANDROID_KEY_STORE)
            keyStore.load(null)
            val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val publicKey: PublicKey = privateKeyEntry.certificate.publicKey

            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val encryptedByte = cipher.doFinal(data)
            encryptedByte.toHex()
        } catch (e: Exception) {

            Log.e("<<Error","ENCRYPTION ERROR: $e")
            ""
        }
    }

    fun encrypt2ByteArr(alias: String, data: ByteArray): ByteArray {
        return try {
            val keyStore = KeyStore.getInstance(SECURITY_PROVIDER_ANDROID_KEY_STORE)
            keyStore.load(null)
            val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val publicKey: PublicKey = privateKeyEntry.certificate.publicKey
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            cipher.doFinal(data)
        } catch (e: Exception) {
            Log.e("<<Error","ENCRYPTION ERROR: $e")
            ByteArray(0)
        }
    }

    fun decryptData(alias: String, encryptedString: String): ByteArray {
        return try {
            val keyStore = KeyStore.getInstance(SECURITY_PROVIDER_ANDROID_KEY_STORE)
            keyStore.load(null)
            val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val privateKey: PrivateKey = privateKeyEntry.privateKey
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            val decryptedByte = encryptedString.hexStringToByteArray()
            cipher.doFinal(decryptedByte)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("<<Error","ENCRYPTION ERROR: $e")
            byteArrayOf()
        }
    }

}