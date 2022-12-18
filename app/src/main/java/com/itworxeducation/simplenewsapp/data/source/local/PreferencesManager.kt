package com.itworxeducation.simplenewsapp.data.source.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val TAG = "PreferencesManager"
private val CLASS_NAME = "com.itworxeducation.simplenewsapp.data.source.local.PreferencesManager"


data class AppPreferences(val isCalledFirstTime: Boolean)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val datastore = context.createDataStore("app_preferences")

    val preferencesFlow = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences: ", exception)
                emit(emptyPreferences())
            } else
                throw exception

        }
        .map { preferences ->

            val isCalledFirstTime = preferences[PreferencesKeys.isCalledFirstTime] ?: true
            AppPreferences(isCalledFirstTime)
        }

    suspend fun setIsCalledFirstTime(isCalledFirstTime: Boolean) {
        datastore.edit { preferences ->

            preferences[PreferencesKeys.isCalledFirstTime] = isCalledFirstTime
        }
    }

    private object PreferencesKeys {
        val isCalledFirstTime = preferencesKey<Boolean>(CLASS_NAME+"isCalledFirstTime")
    }

}