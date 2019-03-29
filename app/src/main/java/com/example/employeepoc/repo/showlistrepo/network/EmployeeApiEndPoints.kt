package com.example.employeepoc.repo.showlistrepo.network

import com.example.employeepoc.repo.showlistrepo.EmployeeEntity
import retrofit2.Call
import retrofit2.http.GET

interface EmployeeApiEndPoints {
    @GET("api/v1/employees")
    fun getEmployeeDetailsFromApi(): Call<List<EmployeeEntity>>
}