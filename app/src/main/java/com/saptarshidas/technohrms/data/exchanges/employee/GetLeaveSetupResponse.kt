package com.saptarshidas.technohrms.data.exchanges.employee

data class GetLeaveSetupResponse(
    val leaveCount: Int,
    val leaveName: String,
    val message: String
)