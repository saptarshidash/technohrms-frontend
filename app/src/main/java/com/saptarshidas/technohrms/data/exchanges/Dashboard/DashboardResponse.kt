package com.saptarshidas.technohrms.data.exchanges.Dashboard

data class DashboardResponse(
    val onGoingTrainings: List<OnGoingTraining>,
    val pendingLeaveList: List<Any>,
    val todayAttendance: Int,
    val totalDepartment: Int,
    val totalEmployee: Int
)