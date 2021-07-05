package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.employee.*
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.LeaveSetup
import retrofit2.http.*

interface EmployeeApi {

    @GET("employee-mgmt/employees")
    suspend fun getAllEmployee(): GetEmployeeResponse

    @GET("departments")
    suspend fun getAllDepartments(): GetDepartmentsResponse

    @GET("designations")
    suspend fun getAllDesignations(): GetDesignationsResponse

    @PATCH("employee-mgmt/employee/{id}")
    suspend fun updateEmployee(@Path("id") id: Int, @Body employee: Employee): Employee

    @POST("leave-mgmt/leave")
    suspend fun saveLeaveSetup(@Body setup: LeaveSetup): GetLeaveSetupResponse

    @GET("leave-mgmt/leave/employee/{id}")
    suspend fun getAllLeaveSetups(@Path("id") id: Int): GetAllLeaveSetupResponse

    @POST("employee-mgmt/employee")
    suspend fun addEmployee(@Body employee: Employee): AddEmployeeResponse
}