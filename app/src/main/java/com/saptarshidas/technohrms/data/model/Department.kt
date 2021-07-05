package com.saptarshidas.technohrms.data.model


data class Department(
    val description: String,
    val id: Int,
    val name: String,
    val totalEmployee: Int
){
    override fun toString(): String {
        return "$id $name"
    }
}




