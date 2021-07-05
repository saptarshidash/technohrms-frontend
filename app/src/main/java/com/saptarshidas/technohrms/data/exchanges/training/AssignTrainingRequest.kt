package com.saptarshidas.technohrms.data.exchanges.training

import com.saptarshidas.technohrms.data.exchanges.employee.EmployeeIdDto


data class AssignTrainingRequest(
    val employee: EmployeeIdDto,
    val training: TrainingIdDto,
    val startDate: String,
    val endDate: String


)