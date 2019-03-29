package com.example.employeepoc.repo.showlistrepo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.employeepoc.repo.showlistrepo.EmployeeEntity

/**
 * Class that contains the methods used for accessing the Room database
 */

@Dao
interface EmployeeListDao {

    /**
     * Method to check whether the database is empty
     * Limits the query to return only one Employee Entity
     *
     * @return Employee Entity
     */
    @Query("SELECT * FROM employee_list LIMIT 1")
    fun hasEmployees(): EmployeeEntity

    /**
     * Method that queries the database and returns the list
     * of all the employees wrapped in LiveData
     */
    @Query("SELECT * FROM employee_list")
    fun getAllEmployees(): LiveData<List<EmployeeEntity>>

    /**
     * Method that inserts a list of EmployeeEntity into the Room database
     *
     * @param employees List of EmployeeEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEmployees(employees: List<EmployeeEntity>?)

    /**
     * Method that deletes an individual EmployeeEntity from the Room database
     * @param employee EmployeeEntity
     */
    @Delete
    fun deleteEmployee(employee: EmployeeEntity)

}