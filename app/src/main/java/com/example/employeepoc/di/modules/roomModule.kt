package com.example.employeepoc.di.modules

import android.arch.persistence.room.Room
import com.example.employeepoc.repo.showlistrepo.database.EmployeeListDatabase
import org.koin.dsl.module.module

val roomModule = module {
    single {
        Room.databaseBuilder(
            get(),
            EmployeeListDatabase::class.java,
            "EmployeeListDB"
        ).build()
    }

    single { get<EmployeeListDatabase>().employeeListDao() }

}

