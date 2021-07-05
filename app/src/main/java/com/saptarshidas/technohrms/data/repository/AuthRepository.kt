package com.saptarshidas.technohrms.data.repository

import android.app.Application
import android.content.Context
import com.saptarshidas.technohrms.data.exchanges.auth.AuthRequest
import com.saptarshidas.technohrms.data.exchanges.auth.UserRegistrationRequest
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.AuthApi

class AuthRepository(context: Context) : BaseRepository(){

    private var prefHelper = AppPreferencesHelper(context)

    private var authApi: AuthApi = RemoteDataSource.createApi(null, AuthApi::class.java)

    suspend fun authenticate(request: AuthRequest) = safeApiCall { authApi.authenticate(request) }

    suspend fun saveAuthToken(token: String?){
        prefHelper.setAccessToken(token)
    }

    suspend fun register(request: UserRegistrationRequest) = safeApiCall {
        authApi.register(request)
    }

}