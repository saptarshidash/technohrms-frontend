package com.saptarshidas.technohrms.data.repository

import android.app.Application
import android.content.Context
import com.saptarshidas.technohrms.data.exchanges.training.AssignTrainingRequest
import com.saptarshidas.technohrms.data.exchanges.training.UpdateAssignedTrainingRequest
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.data.model.Training
import com.saptarshidas.technohrms.data.network.RemoteDataSource
import com.saptarshidas.technohrms.data.network.apis.EmployeeApi
import com.saptarshidas.technohrms.data.network.apis.TrainingApi

class TrainingRepository(context: Context): BaseRepository() {

    private val prefHelper = AppPreferencesHelper(context)

    private val api: TrainingApi = RemoteDataSource.createApi(getAuthToken(prefHelper), TrainingApi::class.java)

    suspend fun getAllTraining() = safeApiCall { api.getAllTraining() }

    suspend fun getAllAssignedTrainingByEmployee(id: Int) = safeApiCall{ api.getAllAssignedTrainingByEmployee(id)}

    suspend fun getAllAssignedTrainingByTraining(id: Int) = safeApiCall { api.getAllAssignedTrainingByTraining(id)  }

    suspend fun addTraining(request: Training) = safeApiCall { api.addTraining(request) }

    suspend fun getAllAssignedTraining() = safeApiCall { api.getAllAssignedTraining() }

    suspend fun assignTraining(request: AssignTrainingRequest) = safeApiCall { api.assignTraining(request) }

    suspend fun updateAssignedTraining(id: Int, request: UpdateAssignedTrainingRequest) =
        safeApiCall { api.updateAssignedTraining(id, request) }

}