package com.saptarshidas.technohrms.data.model

data class Training(
    val completed: Int = 0,
    val description: String,
    val id: Int,
    val name: String,
    val onGoingParticipant: Int = 0,
    val totalParticipation: Int = 0
){
    override fun toString(): String {
        return "id=$id. $name"
    }
}