package com.xoka74.chess.data.auth

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.xoka74.chess.R

class UserInfoManager(context: Context) {

    companion object {
        private const val APP_PREFERENCES = "mysettings"
    }

    val accessTokenKey = getString(context, R.string.access_token)
    val refreshTokenKey = getString(context, R.string.refresh_token)

    private val sharedPrefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    fun getToken(key: String): String? {
        return sharedPrefs.getString(key, null)
    }

    fun saveToken(key: String, token: String) {
        with(sharedPrefs.edit()) {
            putString(key, token)
            apply()
        }
    }

    fun deleteToken(key: String) {
        with(sharedPrefs.edit()) {
            remove(key)
            apply()
        }
    }
}