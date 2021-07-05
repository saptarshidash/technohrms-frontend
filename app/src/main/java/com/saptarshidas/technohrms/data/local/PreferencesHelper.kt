package com.saptarshidas.technohrms.data.local

import kotlinx.coroutines.flow.Flow

interface PreferencesHelper {
    fun getAccessToken(): Flow<String?>
    suspend fun setAccessToken(accessToken: String?)

    suspend fun  setUsername(username: String?)
    fun getUsername(): Flow<String?>
}