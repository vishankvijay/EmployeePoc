package com.example.employeepoc.ui.showlist

import android.arch.lifecycle.LiveData
import com.example.employeepoc.repo.showlistrepo.EmployeeEntity
import com.example.employeepoc.utils.Status

interface IShowList {
    interface View {
        fun setUpActionBarTitle(dataSourceTypeValue: String)
        fun setUpViewModel(dataSourceTypeValue: String)
        fun setUpStatusLiveData(employeeListViewModel: EmployeeListViewModel)
        fun setUpEmployeeListLiveData(employeeListViewModel: EmployeeListViewModel, dataSourceTypeValue: String)
    }

    interface ViewModel {
        fun getEmployeesList(dataSourceTypeValue: String): LiveData<List<EmployeeEntity>>?
        fun getStatusLiveData(): LiveData<Status>?
        fun deleteEmployee(employee: EmployeeEntity)
    }
}