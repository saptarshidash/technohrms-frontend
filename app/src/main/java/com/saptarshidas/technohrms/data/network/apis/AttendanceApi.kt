package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.attendance.GetAttendanceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AttendanceApi {

    @GET("attendances")
    suspend fun getAllAttendanceByDateRange(
        @Query("start") param1: String,
        @Query("end") param2: String
    ): GetAttendanceResponse

    @GET("attendances/employee")
    suspend fun getAllAttendanceByEmpAndDate(
        @Query("id") param1: Int,
        @Query("start") param2: String,
        @Query("end") param3: String
    ): GetAttendanceResponse

}