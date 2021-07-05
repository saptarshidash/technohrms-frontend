package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.leave.ApproveLeaveRequest
import com.saptarshidas.technohrms.data.exchanges.leave.ApproveLeaveResponse
import com.saptarshidas.technohrms.data.exchanges.leave.GetLeaveRequestResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface LeaveApi {

    @GET("leave-mgmt/leave/employee/requests")
    suspend fun getAllLeaveRequests(): GetLeaveRequestResponse

    @GET("leave-mgmt/leave/employee/request")
    suspend fun getAllLeaveRequestsByEmployee(
        @Query("id") id: Int
    ): GetLeaveRequestResponse

    @PATCH("leave-mgmt/leave/employee/request")
    suspend fun approveLeaveRequest(@Body request: ApproveLeaveRequest): ApproveLeaveResponse

}