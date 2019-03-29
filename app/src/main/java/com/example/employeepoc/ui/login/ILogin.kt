package com.example.employeepoc.ui.login

import android.arch.lifecycle.LiveData

interface ILogin {
    interface View {

        fun showAuthorizationHelpAlertDialog()
    }

    interface ViewModel {

        fun getLoginLiveData(): LiveData<Boolean>
        fun getHelpLiveData(): LiveData<Boolean>
        fun onHelpClicked()
        fun onLoginClicked()
    }
}