package com.saptarshidas.technohrms.data.repository

import android.content.Context
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.LeaveSetup
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.EmployeeApi

class EmployeeRepository(context: Context) : BaseRepository() {

    private val prefHelper = AppPreferencesHelper(context)

    private val api: EmployeeApi = RemoteDataSource.createApi(getAuthToken(prefHelper), EmployeeApi::class.java)


    suspend fun getAllEmployees() = safeApiCall { api.getAllEmployee() }

    suspend fun getAllDepartments() = safeApiCall { api.getAllDepartments() }

    suspend fun getAllDesignations() = safeApiCall { api.getAllDesignations() }

    suspend fun updateEmployee(employee: Employee) = safeApiCall { api.updateEmployee(employee.id, employee) }

    suspend fun saveLeaveSetup(setup: LeaveSetup) = safeApiCall { api.saveLeaveSetup(setup) }


    suspend fun getAllLeaveSetups(id: Int) = safeApiCall { api.getAllLeaveSetups(id) }

    suspend fun addEmployee(request: Employee) = safeApiCall {
        api.addEmployee(request)
    }

}