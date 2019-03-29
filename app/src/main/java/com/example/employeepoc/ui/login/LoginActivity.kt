package com.example.employeepoc.ui.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.employeepoc.R
import com.example.employeepoc.databinding.ActivityLoginBinding
import com.example.employeepoc.ui.selectdatasource.SelectDataSourceActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(), ILogin.View {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)


        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

        loginViewModel.getLoginLiveData().observe(this,
            Observer {
                Toast.makeText(this, getString(R.string.msg_login_success), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SelectDataSourceActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            })

        loginViewModel.getHelpLiveData().observe(this,
            Observer {
                showAuthorizationHelpAlertDialog()
            })
    }

    /**
     *Shows Alert Dialog explaining the format of a valid user name and password.
     */
    override fun showAuthorizationHelpAlertDialog() {
        val alertDialog = AlertDialog.Builder(this@LoginActivity).create()
        alertDialog.setTitle(getString(R.string.msg_authentication_help_title))
        alertDialog.setMessage(getString(R.string.authentication_help_message))
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}
