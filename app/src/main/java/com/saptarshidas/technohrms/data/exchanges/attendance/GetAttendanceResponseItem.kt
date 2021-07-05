package com.saptarshidas.technohrms.data.exchanges.attendance

import com.saptarshidas.technohrms.data.model.Employee

data class GetAttendanceResponseItem(
    val date: String,
    val employee: Employee,
    val id: Int,
    val inTime: String,
    val outTime: String
)