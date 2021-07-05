package com.saptarshidas.technohrms.data.exchanges.department

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DepartmentDto (
    val description: String,
    val id: Int,
    val name: String,
    val totalEmployee: Int): Parcelable