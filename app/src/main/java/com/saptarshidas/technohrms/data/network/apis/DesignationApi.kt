package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.designation.AddDesignationRequest
import com.saptarshidas.technohrms.data.exchanges.designation.AddDesignationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DesignationApi {

    @POST("designation")
    suspend fun addDesignation(@Body request: AddDesignationRequest): AddDesignationResponse
}