package com.example.employeepoc.repo.showlistrepo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Entity class that represents a table within the Room database.
 * This class also serves as a model class for Retrofit to map the response from the Api
 */

@Entity(tableName = "employee_list")
data class EmployeeEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "employee_name")
    @SerializedName("employee_name")
    val employeeName: String
)