package com.saptarshidas.technohrms.data.exchanges.Dashboard

import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.Training

data class OnGoingTraining(
    val completionStatus: Boolean,
    val employee: Employee,
    val endDate: String,
    val id: Int,
    val rating: Int,
    val startDate: String,
    val training: Training
)