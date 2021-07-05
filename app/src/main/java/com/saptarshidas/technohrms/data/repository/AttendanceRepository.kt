package com.saptarshidas.technohrms.data.repository

import android.content.Context
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.AttendanceApi
import com.saptarshidas.technohrms.data.network.apis.AuthApi

class AttendanceRepository(context: Context): BaseRepository() {

    private var prefHelper = AppPreferencesHelper(context)

    private var api: AttendanceApi = RemoteDataSource.createApi(getAuthToken(prefHelper), AttendanceApi::class.java)

    suspend fun getAllAttendanceByDate(st: String, end: String) = safeApiCall {
        api.getAllAttendanceByDateRange(st, end)
    }

    suspend fun getAllAttendanceByEmpAndDate(id:Int, st: String, end: String) = safeApiCall {
        api.getAllAttendanceByEmpAndDate(id,st, end)
    }
}