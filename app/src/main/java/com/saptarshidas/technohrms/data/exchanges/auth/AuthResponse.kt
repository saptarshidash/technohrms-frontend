package com.saptarshidas.technohrms.data.exchanges.auth

data class AuthResponse(
    val jwt: String,
    val role: List<Role>,
    val username: String
)