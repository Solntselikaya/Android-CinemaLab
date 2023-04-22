package com.example.cinemalab.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto

class SharedPreferencesStorage(context: Context) : TokenStorage {

    companion object {
        const val ENCRYPTED_SHARED_PREFS_NAME = "encryptedSharedPreferences"
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        ENCRYPTED_SHARED_PREFS_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


    override fun saveToken(token: AuthTokenPairDto) {
        sharedPreferences.edit()
            .putString(TokenStorage.TOKEN_KEY, token.accessToken)
            .putString(TokenStorage.REFRESH_TOKEN_KEY, token.refreshToken)
            .putInt(TokenStorage.TOKEN_EXPIRE_TIME, token.accessTokenExpiresIn)
            .putInt(TokenStorage.REFRESH_TOKEN_EXPIRE_TIME, token.refreshTokenExpiresIn)
            .apply()
    }

    override fun getToken(): AuthTokenPairDto {
        return AuthTokenPairDto(
            sharedPreferences.getString(
                TokenStorage.TOKEN_KEY, ""
            ).toString(),
            sharedPreferences.getInt(
                TokenStorage.TOKEN_EXPIRE_TIME, 0
            ),
            sharedPreferences.getString(
                TokenStorage.REFRESH_TOKEN_KEY, ""
            ).toString(),
            sharedPreferences.getInt(
                TokenStorage.REFRESH_TOKEN_EXPIRE_TIME, 0
            )
        )
    }

    override fun deleteToken() {
        sharedPreferences.edit()
            .remove(TokenStorage.TOKEN_KEY)
            .remove(TokenStorage.REFRESH_TOKEN_KEY)
            .apply()
    }
}