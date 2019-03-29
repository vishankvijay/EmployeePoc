package com.example.employeepoc.repo.showlistrepo

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.employeepoc.R
import com.example.employeepoc.repo.showlistrepo.database.EmployeeListDao
import com.example.employeepoc.repo.showlistrepo.database.RoomExecutor
import com.example.employeepoc.repo.showlistrepo.network.EmployeeApiEndPoints
import com.example.employeepoc.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EmployeeRepository(
    application: Application,
    private val webService: EmployeeApiEndPoints,
    private val roomDao: EmployeeListDao
) {

    private val context: Context = application.applicationContext
    private val statusLiveData = MutableLiveData<Status>()
    private val LOGTAG = EmployeeRepository::class.java.simpleName
    //  val roomDbDAO = EmployeeListDatabase.getDatabaseInstance(context)?.employeeListDao()

    /**
     * Depending on the data source type,
     * makes a call to Room DB to get locally stored data
     * or a Retrofit call to fetch data from API
     *
     * @param dataSourceTypeValue The type of data source selected by user
     */
    fun getEmployeesList(dataSourceTypeValue: String): LiveData<List<EmployeeEntity>>? {
        if (dataSourceTypeValue == context.getString(R.string.api_call))
            return getEmployeesListFromApi()
        return getEmployeesListFromDb()
    }

    fun getStatusLiveData(): LiveData<Status>? {
        return statusLiveData
    }

    /**
     * Makes a call to Room DB to get locally stored data
     */
    private fun getEmployeesListFromDb(): LiveData<List<EmployeeEntity>>? {
        refreshDatabaseIfEmpty()
        return roomDao.getAllEmployees()
    }

    /**
     * If the Room DB is empty then makes a Retrofit call to fetch data
     * and then inserts data in the database.
     */
    private fun refreshDatabaseIfEmpty() {
        RoomExecutor.instance?.diskIO?.execute {
            val doesRoomDbHasEmployees = (roomDao.hasEmployees() != null)
            if (!doesRoomDbHasEmployees) {
                //val employeeRetrofitClient = EmployeeRetrofitClient.create()
                webService.getEmployeeDetailsFromApi()
                    .enqueue(object : Callback<List<EmployeeEntity>> {
                        override fun onFailure(call: Call<List<EmployeeEntity>>, t: Throwable) {
                            if (t is IOException)
                                statusLiveData.value = Status.DISCONNECTED
                        }

                        override fun onResponse(
                            call: Call<List<EmployeeEntity>>,
                            response: Response<List<EmployeeEntity>>
                        ) {
                            statusLiveData.value = Status.SUCCESS
                            RoomExecutor.instance?.diskIO!!.execute {
                                roomDao.insertAllEmployees(response.body())
                            }
                        }
                    })
            } else
                statusLiveData.postValue(Status.SUCCESS)
        }
    }

    /**
     * Makes a Retrofit call to API to fetch employee list
     */
    private fun getEmployeesListFromApi(): MutableLiveData<List<EmployeeEntity>> {
        val employeeList = MutableLiveData<List<EmployeeEntity>>()
        //  val employeeRetrofitClient = EmployeeRetrofitClient.create()
        webService.getEmployeeDetailsFromApi()
            .enqueue(object : Callback<List<EmployeeEntity>> {

                override fun onFailure(call: Call<List<EmployeeEntity>>, t: Throwable) {
                    if (t is IOException)
                        statusLiveData.value = Status.DISCONNECTED
                }

                override fun onResponse(
                    call: Call<List<EmployeeEntity>>,
                    response: Response<List<EmployeeEntity>>
                ) {
                    statusLiveData.value = Status.SUCCESS
                    employeeList.value = response.body()
                    Log.d(LOGTAG, "Retrofit onResponse called")
                }
            })
        return employeeList
    }

    /**
     * Makes a call to Room Dao to delete an individual employee entity
     * @param employee Employee entity to be deleted
     */
    fun deleteEmployee(employee: EmployeeEntity) {
        RoomExecutor.instance?.diskIO?.execute {
            roomDao.deleteEmployee(employee)
        }
    }
}