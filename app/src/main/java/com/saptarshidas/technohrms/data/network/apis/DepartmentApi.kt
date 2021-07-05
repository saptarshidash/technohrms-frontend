package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.department.AddDepartmentResponse
import com.saptarshidas.technohrms.data.model.Department
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface DepartmentApi {

    @POST("department")
    suspend fun addDepartment(@Body department: Department): AddDepartmentResponse

    @PATCH("department/{id}")
    suspend fun updateDepartment(@Path("id") id: Int, @Body department: Department): Department

}