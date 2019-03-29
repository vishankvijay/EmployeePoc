package com.example.employeepoc.ui.showlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.employeepoc.repo.showlistrepo.EmployeeEntity
import com.example.employeepoc.repo.showlistrepo.EmployeeRepository
import com.example.employeepoc.utils.Status

class EmployeeListViewModel(val employeeDetailsRepository: EmployeeRepository) : ViewModel(),
    IShowList.ViewModel {

    var animationCount = 0
    private var employeeList: LiveData<List<EmployeeEntity>>? = null
    private var statusLiveData: LiveData<Status>? = null


    /**
     * Gets the list of employees from repository
     */

    override fun getEmployeesList(dataSourceTypeValue: String): LiveData<List<EmployeeEntity>>? {
        if (employeeList == null)
            employeeList = employeeDetailsRepository.getEmployeesList(dataSourceTypeValue)

        return employeeList
    }

    override fun getStatusLiveData(): LiveData<Status>? {
        if (statusLiveData == null)
            statusLiveData = employeeDetailsRepository.getStatusLiveData()
        return statusLiveData
    }

    /**
     * Makes a call to repository to delete an individual employee entity
     * @param employee Employee entity to be deleted
     */
    override fun deleteEmployee(employee: EmployeeEntity) {
        employeeDetailsRepository.deleteEmployee(employee)
    }
}