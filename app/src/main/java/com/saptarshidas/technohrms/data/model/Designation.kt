package com.saptarshidas.technohrms.data.model

data class Designation(
    val description: String,
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return "$id $name"
    }
}