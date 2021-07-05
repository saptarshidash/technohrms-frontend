package com.saptarshidas.technohrms.data.repository

import android.content.Context
import com.saptarshidas.technohrms.data.exchanges.designation.AddDesignationRequest
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.DepartmentApi
import com.saptarshidas.technohrms.data.network.apis.DesignationApi

class DesignationRepository(context: Context): BaseRepository() {


    private val prefHelper = AppPreferencesHelper(context)
    private val api: DesignationApi = RemoteDataSource.createApi(getAuthToken(prefHelper), DesignationApi::class.java)

    suspend fun addDesignation(request: AddDesignationRequest) = safeApiCall {
        api.addDesignation(request)
    }
}