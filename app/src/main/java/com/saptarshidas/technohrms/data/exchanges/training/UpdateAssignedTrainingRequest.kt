package com.saptarshidas.technohrms.data.exchanges.training

import com.saptarshidas.technohrms.data.exchanges.employee.EmployeeIdDto
import com.saptarshidas.technohrms.data.model.Training

data class UpdateAssignedTrainingRequest(

    val employeeIdDto: EmployeeIdDto,
    val training: Training,
    val completionStatus: Boolean,
    val endDate: String,
    val rating: Int,
    val startDate: String
)