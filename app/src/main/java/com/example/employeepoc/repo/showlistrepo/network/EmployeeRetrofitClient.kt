package com.example.employeepoc.repo.showlistrepo.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmployeeRetrofitClient {

    /**
     * Companion object to create the EmployeeApiEndPoints
     */
    companion object {
        private const val BASE_URL = "http://dummy.restapiexample.com/"
        fun create(): EmployeeApiEndPoints {
            val retrofitInstance = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .build()
            return retrofitInstance.create(EmployeeApiEndPoints::class.java)
        }

        private fun getOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }
    }
}