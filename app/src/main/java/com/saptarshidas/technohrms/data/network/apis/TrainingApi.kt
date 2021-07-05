package com.saptarshidas.technohrms.data.network.apis

import com.saptarshidas.technohrms.data.exchanges.training.*
import com.saptarshidas.technohrms.data.model.Training
import retrofit2.http.*

interface TrainingApi {

    @GET("training-mgmt/trainings")
    suspend fun getAllTraining(): GetAllTrainingResponse

    @GET("training-mgmt/training/employee/assigned")
    suspend fun getAllAssignedTrainingByEmployee(
        @Query("id") id: Int
    ): GetAssignedTrainingResponse

    @GET("training-mgmt/training/employee/assigned/training")
    suspend fun getAllAssignedTrainingByTraining(
        @Query("id") id: Int
    ): GetAssignedTrainingResponse


    @POST("training-mgmt/training")
    suspend fun addTraining(@Body request: Training): AddTrainingResponse

    @GET("training-mgmt/training/employees/assigned")
    suspend fun getAllAssignedTraining(): GetAssignedTrainingResponse

    @POST("training-mgmt/training/employee")
    suspend fun assignTraining(@Body request: AssignTrainingRequest): AssignTrainingResponse

    @PATCH("training-mgmt/training/employee/assigned/{id}")
    suspend fun updateAssignedTraining(@Path("id") id: Int, @Body request: UpdateAssignedTrainingRequest)
    : UpdateAssignedTrainingResponse

}