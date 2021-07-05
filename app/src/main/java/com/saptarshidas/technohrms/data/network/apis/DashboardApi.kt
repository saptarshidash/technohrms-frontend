package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.Dashboard.DashboardResponse
import retrofit2.http.GET

interface DashboardApi {

    @GET("dashboard")
    suspend fun getDashboardResponse(): DashboardResponse
}