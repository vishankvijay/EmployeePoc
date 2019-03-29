package com.example.employeepoc.di.modules

import com.example.employeepoc.di.modules.Properties.BASE_URL
import com.example.employeepoc.repo.showlistrepo.network.EmployeeApiEndPoints
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    single { createWebService(get(), getProperty(BASE_URL)) }
    single { createOkHttpClient() }
}

fun createWebService(okHttpClient: OkHttpClient, url: String): EmployeeApiEndPoints {
    val retrofitInstance = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .client(okHttpClient)
        .build()
    return retrofitInstance.create(EmployeeApiEndPoints::class.java)
}

fun createOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

object Properties {
    const val BASE_URL = "BASE_URL"
}
