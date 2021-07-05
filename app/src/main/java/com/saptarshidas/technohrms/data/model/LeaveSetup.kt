package com.saptarshidas.technohrms.data.model

data class LeaveSetup(
    val employee: Employee,
    val leaveName: String,
    val totalLeave: Int
)