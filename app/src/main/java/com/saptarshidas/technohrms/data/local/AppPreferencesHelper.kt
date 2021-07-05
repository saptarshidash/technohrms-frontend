package com.saptarshidas.technohrms.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class AppPreferencesHelper(context: Context) : PreferencesHelper {

    private val applicationContext = context.applicationContext

    private val dataStore: DataStore<Preferences> = applicationContext.dataStore

    companion object {
        private val PREF_KEY_ACCESS_TOKEN = stringPreferencesKey("auth_key")
        private val PREF_KEY_USERNAME = stringPreferencesKey("username")
    }


    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[PREF_KEY_ACCESS_TOKEN] }
    }

    override suspend fun setAccessToken(accessToken: String?) {
        dataStore.edit { token -> token[PREF_KEY_ACCESS_TOKEN] = accessToken.toString() }
    }

    override suspend fun setUsername(username: String?) {
        dataStore.edit { user -> user[PREF_KEY_USERNAME] = username.toString() }
    }

    override fun getUsername(): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[PREF_KEY_USERNAME] }
    }

}