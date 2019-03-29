package com.example.employeepoc.di.modules

import com.example.employeepoc.repo.showlistrepo.EmployeeRepository
import com.example.employeepoc.ui.login.LoginViewModel
import com.example.employeepoc.ui.showlist.EmployeeListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { EmployeeListViewModel(get()) }

    single { EmployeeRepository(get(), get(), get()) }
}