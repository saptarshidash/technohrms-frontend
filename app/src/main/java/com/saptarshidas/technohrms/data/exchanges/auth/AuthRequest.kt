package com.saptarshidas.technohrms.data.exchanges.auth

class AuthRequest(val username: String, val password: String) {

    public fun checkValidation() : Boolean{

        return if(username.isEmpty() || password.isEmpty() )
            false
        else
            false
    }
}