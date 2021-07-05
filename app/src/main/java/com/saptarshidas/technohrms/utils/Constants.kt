package com.saptarshidas.technohrms.utils

class Constants {
    companion object {
        const val APP_PACKAGE = "com.saptarshidas.technohrms"
        const val BASE_URL = "http://localhost:8081/"
        const val CONNECTION_TIMEOUT: Long = 5 // 10 seconds
        const val READ_TIMEOUT: Long = 5 // 2 seconds
        const val WRITE_TIMEOUT: Long = 5 // 2 seconds

        // fragments
        const val FRAGMENT_DASHBOARD = 0
        const val FRAGMENT_ENTITY = 1
        const val FRAGMENT_PROFILE = 2

        // transaction type
        const val TRANSACTION_CREDIT = "CREDIT"
        const val TRANSACTION_DEBIT = "DEBIT"

        // entity type
        const val ENTITY_CUSTOMER = 0
        const val ENTITY_VENDOR = 1
    }
}