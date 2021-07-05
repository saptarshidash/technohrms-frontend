package com.saptarshidas.technohrms.data.repository

import android.content.Context
import com.saptarshidas.technohrms.data.exchanges.leave.ApproveLeaveRequest
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.LeaveApi

class LeaveRepository(context: Context): BaseRepository() {

    private val prefHelper = AppPreferencesHelper(context)

    private val api: LeaveApi = RemoteDataSource.createApi(getAuthToken(prefHelper), LeaveApi::class.java)

    suspend fun getAllLeaveRequests() = safeApiCall { api.getAllLeaveRequests() }

    suspend fun approveLeaveRequest(request: ApproveLeaveRequest) = safeApiCall {
        api.approveLeaveRequest(request)
    }

    suspend fun getAllLeaveRequestByEmployee(id: Int) = safeApiCall { api.getAllLeaveRequestsByEmployee(id) }

}