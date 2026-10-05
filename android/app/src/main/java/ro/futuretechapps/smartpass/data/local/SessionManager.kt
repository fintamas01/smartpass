package ro.futuretechapps.smartpass.data.local

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SessionManager(
    context: Context
) {

    private val preferences = context.getSharedPreferences(
        "smartpass_session",
        Context.MODE_PRIVATE
    )

    private val keyStore = KeyStore
        .getInstance("AndroidKeyStore")
        .apply {
            load(null)
        }

    init {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            generateKey()
        }
    }

    private fun generateKey() {

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )

        val keySpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or
                    KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(
                KeyProperties.BLOCK_MODE_GCM
            )
            .setEncryptionPaddings(
                KeyProperties.ENCRYPTION_PADDING_NONE
            )
            .setKeySize(256)
            .build()

        keyGenerator.init(keySpec)

        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {

        val entry = keyStore.getEntry(
            KEY_ALIAS,
            null
        ) as KeyStore.SecretKeyEntry

        return entry.secretKey
    }

    fun saveToken(token: String) {

        val cipher = Cipher.getInstance(
            TRANSFORMATION
        )

        cipher.init(
            Cipher.ENCRYPT_MODE,
            getSecretKey()
        )

        val encryptedToken = cipher.doFinal(
            token.toByteArray(Charsets.UTF_8)
        )

        val encryptedTokenString =
            Base64.encodeToString(
                encryptedToken,
                Base64.NO_WRAP
            )

        val ivString =
            Base64.encodeToString(
                cipher.iv,
                Base64.NO_WRAP
            )

        preferences
            .edit()
            .putString(
                TOKEN_KEY,
                encryptedTokenString
            )
            .putString(
                IV_KEY,
                ivString
            )
            .apply()
    }

    fun getToken(): String? {

        val encryptedTokenString =
            preferences.getString(
                TOKEN_KEY,
                null
            ) ?: return null

        val ivString =
            preferences.getString(
                IV_KEY,
                null
            ) ?: return null

        return try {

            val encryptedToken =
                Base64.decode(
                    encryptedTokenString,
                    Base64.NO_WRAP
                )

            val iv =
                Base64.decode(
                    ivString,
                    Base64.NO_WRAP
                )

            val cipher =
                Cipher.getInstance(
                    TRANSFORMATION
                )

            cipher.init(
                Cipher.DECRYPT_MODE,
                getSecretKey(),
                GCMParameterSpec(
                    128,
                    iv
                )
            )

            val decryptedToken =
                cipher.doFinal(
                    encryptedToken
                )

            String(
                decryptedToken,
                Charsets.UTF_8
            )

        } catch (exception: Exception) {

            clearToken()

            null
        }
    }

    fun clearToken() {

        preferences
            .edit()
            .remove(TOKEN_KEY)
            .remove(IV_KEY)
            .apply()
    }

    fun hasToken(): Boolean {
        return getToken() != null
    }

    companion object {

        private const val KEY_ALIAS =
            "smartpass_session_key"

        private const val TOKEN_KEY =
            "access_token"

        private const val IV_KEY =
            "access_token_iv"

        private const val TRANSFORMATION =
            "AES/GCM/NoPadding"
    }
}