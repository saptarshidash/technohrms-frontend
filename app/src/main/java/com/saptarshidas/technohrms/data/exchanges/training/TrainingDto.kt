package com.saptarshidas.technohrms.data.exchanges.training

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrainingDto(
    val completed: Int,
    val description: String,
    val id: Int,
    val name: String,
    val onGoingParticipant: Int,
    val totalParticipation: Int
): Parcelable{
    override fun toString(): String {
        return "$id - $name"
    }
}