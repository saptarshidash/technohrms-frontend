package com.saptarshidas.technohrms.data.network

import com.saptarshidas.technohrms.utils.Constants.Companion.READ_TIMEOUT
import com.saptarshidas.technohrms.utils.Constants.Companion.WRITE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {
    companion object {
        const val BASE_URL = "http://192.168.31.164:8081/"

        fun<Api> createApi(token: String?, api: Class<Api>): Api {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            var handleToken = token

            if(token.isNullOrBlank())
                handleToken = ""
            else
                handleToken = "Bearer $token"


            val client: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    var newRequest: Request = chain.request().newBuilder()
                        .addHeader("Authorization", handleToken)
                        .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build()



            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(api)
        }
    }
}