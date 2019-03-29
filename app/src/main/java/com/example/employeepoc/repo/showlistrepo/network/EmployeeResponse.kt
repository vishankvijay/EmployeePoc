package com.example.employeepoc.repo.showlistrepo.network

import com.google.gson.annotations.SerializedName

class EmployeeResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("employee_name") val employeeName: String
)