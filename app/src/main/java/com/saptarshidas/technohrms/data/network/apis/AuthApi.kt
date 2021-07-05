package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.auth.AuthRequest
import com.saptarshidas.technohrms.data.exchanges.auth.AuthResponse
import com.saptarshidas.technohrms.data.exchanges.auth.UserRegistrationRequest
import com.saptarshidas.technohrms.data.exchanges.auth.UserRegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("authenticate")
    suspend fun authenticate(@Body reqBody: AuthRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body request: UserRegistrationRequest): UserRegistrationResponse
}