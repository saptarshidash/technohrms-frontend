package com.saptarshidas.technohrms.data.exchanges.auth

import com.saptarshidas.technohrms.data.model.Employee

data class UserRegistrationRequest(
    val username: String,
    val password: String,
    val active: Boolean = true,
    val roles: String,
    val employee: Employee
)
