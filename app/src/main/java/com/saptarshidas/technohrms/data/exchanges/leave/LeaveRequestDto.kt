package com.saptarshidas.technohrms.data.exchanges.leave

import android.os.Parcelable
import com.saptarshidas.technohrms.data.model.Employee
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeaveRequestDto(
    val employee: Employee,
    val endDate: String,
    val id: Int,
    val leaveName: String,
    val reason: String,
    val startDate: String,
    val status: String
): Parcelable