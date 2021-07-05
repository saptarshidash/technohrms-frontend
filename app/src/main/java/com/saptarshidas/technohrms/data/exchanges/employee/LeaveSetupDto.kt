package com.saptarshidas.technohrms.data.exchanges.employee

data class LeaveSetupDto(
    val id: Int,
    val leaveName: String,
    val pendingLeave: Int,
    val totalLeave: Int,
    val usedLeave: Int
)