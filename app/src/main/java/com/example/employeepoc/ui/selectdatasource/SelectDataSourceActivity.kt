package com.example.employeepoc.ui.selectdatasource

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.employeepoc.R
import com.example.employeepoc.utils.DATA_SOURCE_TYPE_KEY
import com.example.employeepoc.ui.showlist.EmployeeListActivity
import kotlinx.android.synthetic.main.activity_select_data_source.*

class SelectDataSourceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        var dataSourceTypeValue: String
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_data_source)

        setActionBarTitle()
        button_room_db.setOnClickListener {
            dataSourceTypeValue = getString(R.string.room_db)
            startEmployeeDetailsActivity(dataSourceTypeValue)
        }

        button_call_api.setOnClickListener {
            dataSourceTypeValue = getString(R.string.api_call)
            startEmployeeDetailsActivity(dataSourceTypeValue)
        }
    }

    /**
     * sets the title of the action bar
     */
    private fun setActionBarTitle() {
        supportActionBar?.title = getString(R.string.msg_select_data_source)
    }

    /**
     * Creates a new intent, puts dataSourceTypeValue as extra
     * and starts EmployeeListActivity
     * @param dataSourceTypeValue the type of data source selected by the user
     */
    private fun startEmployeeDetailsActivity(dataSourceTypeValue: String) {
        val intent = Intent(this, EmployeeListActivity::class.java)
        intent.putExtra(DATA_SOURCE_TYPE_KEY, dataSourceTypeValue)
        startActivity(intent)
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }
}
