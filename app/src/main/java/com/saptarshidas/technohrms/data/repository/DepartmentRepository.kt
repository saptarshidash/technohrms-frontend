package com.saptarshidas.technohrms.data.repository

import android.content.Context
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.model.Department
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.DepartmentApi
import com.saptarshidas.technohrms.data.network.apis.EmployeeApi

class DepartmentRepository(context: Context): BaseRepository() {

    private val prefHelper = AppPreferencesHelper(context)

    private val api: DepartmentApi = RemoteDataSource.createApi(getAuthToken(prefHelper), DepartmentApi::class.java)


    suspend fun addDepartment(request: Department) = safeApiCall { api.addDepartment(request) }

    suspend fun updateDepartment(request: Department) = safeApiCall { api.updateDepartment(request.id, request) }


}