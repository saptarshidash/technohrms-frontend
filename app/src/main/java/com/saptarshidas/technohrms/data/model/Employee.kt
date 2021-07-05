package com.saptarshidas.technohrms.data.model

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize

data class Employee(

    val id: Int,
    var name: String,
    var department:@RawValue Department,
    var designation:@RawValue Designation,
    var dob:@RawValue Any,
    val email: String,

    var joiningDate:@RawValue Any,
    var mobile: String,

    var workType: String
): Parcelable {

    override fun toString(): String {
        return "$id - $name"
    }
}