package com.saptarshidas.technohrms.data.repository

import android.content.Context
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.DashboardApi
import com.saptarshidas.technohrms.data.network.apis.TrainingApi

class DashboardRepository(context: Context): BaseRepository() {

    private val prefHelper = AppPreferencesHelper(context)

    private val api: DashboardApi = RemoteDataSource.createApi(getAuthToken(prefHelper), DashboardApi::class.java)

    suspend fun getDashboardData() = safeApiCall { api.getDashboardResponse() }
}