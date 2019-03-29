package com.example.employeepoc.ui.showlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import com.example.employeepoc.R
import com.example.employeepoc.repo.showlistrepo.EmployeeEntity
import com.example.employeepoc.ui.showlist.adapter.RecyclerViewBindingAdapter
import com.example.employeepoc.utils.DATA_SOURCE_TYPE_KEY
import com.example.employeepoc.utils.Status
import kotlinx.android.synthetic.main.activity_employee_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class EmployeeListActivity : AppCompatActivity(), RecyclerViewBindingAdapter.DeleteImageClickListener,
    IShowList.View {

    //private lateinit var employeeListViewModel: EmployeeListViewModel
    private val employeeListViewModel: EmployeeListViewModel by viewModel()
    private lateinit var adapter: RecyclerViewBindingAdapter
    private var isSourceDb: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        val dataSourceTypeValue = intent.getStringExtra(DATA_SOURCE_TYPE_KEY)
        isSourceDb = dataSourceTypeValue == getString(R.string.room_db)

        setUpActionBarTitle(dataSourceTypeValue)

        recycler_view_employee_list.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewBindingAdapter(this)
        recycler_view_employee_list.adapter = adapter

        setUpViewModel(dataSourceTypeValue)
    }

    /**
     * Method that sets the title of the actionBAr
     * depending on the type of data source selected by the user
     *
     * @param dataSourceTypeValue the type of data source selected by the user
     */
    override fun setUpActionBarTitle(dataSourceTypeValue: String) {
        supportActionBar?.title = dataSourceTypeValue
    }


    override fun setUpViewModel(dataSourceTypeValue: String) {
        // employeeListViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel::class.java)
        setUpEmployeeListLiveData(employeeListViewModel, dataSourceTypeValue)
        setUpStatusLiveData(employeeListViewModel)
    }

    override fun setUpStatusLiveData(employeeListViewModel: EmployeeListViewModel) {
        employeeListViewModel.getStatusLiveData()?.observe(this,
            Observer<Status> {
                when (it) {
                    Status.SUCCESS -> progress_bar.visibility = View.GONE
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.DISCONNECTED -> {
                        Toast.makeText(
                            this, getString(R.string.msg_disconnected)
                            , Toast.LENGTH_SHORT
                        ).show()
                        progress_bar.visibility = View.GONE
                    }

                    null -> TODO()
                }
            }
        )
    }

    override fun setUpEmployeeListLiveData(
        employeeListViewModel: EmployeeListViewModel,
        dataSourceTypeValue: String
    ) {
        employeeListViewModel.getEmployeesList(dataSourceTypeValue)?.observe(this,
            Observer<List<EmployeeEntity>> {
                if (it != null) {
                    if (!it.isEmpty())
                        adapter.setEmployeeList(it)
                    if (employeeListViewModel.animationCount == 0) {
                        employeeListViewModel.animationCount = 1
                        val controller: LayoutAnimationController =
                            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom)
                        recycler_view_employee_list.layoutAnimation = controller
                        recycler_view_employee_list.scheduleLayoutAnimation()
                    }
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    /**
     * Handles click of delete button present in employee_list_item.xml
     */
    override fun onDeleteImageClicked(employee: EmployeeEntity) {
        if (isSourceDb)
            employeeListViewModel.deleteEmployee(employee)
    }
}
