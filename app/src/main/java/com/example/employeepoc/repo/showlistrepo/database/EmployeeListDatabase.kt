package com.example.employeepoc.repo.showlistrepo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.employeepoc.repo.showlistrepo.EmployeeEntity

/**
 * Room Database class
 */
@Database(entities = [EmployeeEntity::class], version = 2, exportSchema = false)
abstract class EmployeeListDatabase : RoomDatabase() {

    /**
     * Abstract method returns the EmployeeListDao class that is annotated with
     *
     * @return EmployeeListDao
     */
    abstract fun employeeListDao(): EmployeeListDao

}