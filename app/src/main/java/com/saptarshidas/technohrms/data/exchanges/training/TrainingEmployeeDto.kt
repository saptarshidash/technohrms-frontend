package com.saptarshidas.technohrms.data.exchanges.training

import android.os.Parcelable
import com.saptarshidas.technohrms.data.model.Employee
import com.saptarshidas.technohrms.data.model.Training
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TrainingEmployeeDto(
    val completionStatus: Boolean,
    val employee: @RawValue Employee,
    val endDate: String,
    val id: Int,
    val rating: Int,
    val startDate: String,
    val training:@RawValue Training
): Parcelable