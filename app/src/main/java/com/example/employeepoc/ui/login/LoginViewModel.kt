package com.example.employeepoc.ui.login

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.employeepoc.R
import com.example.employeepoc.utils.REGEX_EMAIL_PATTERN
import com.example.employeepoc.utils.REGEX_PASSWORD_PATTERN

class LoginViewModel(application: Application) : ViewModel(), ILogin.ViewModel {
    private val context = application
    private val regexUserName = Regex(REGEX_EMAIL_PATTERN)
    private val regexPassword = Regex(REGEX_PASSWORD_PATTERN)

    var userName = ""
    var password = ""

    var errorPassword = ObservableField<String>()
    var errorUserName = ObservableField<String>()


    private var loginLiveData: MutableLiveData<Boolean>? = null
    var helpLiveData: MutableLiveData<Boolean>? = null

    override fun getLoginLiveData(): LiveData<Boolean> {
        if (loginLiveData == null)
            loginLiveData = MutableLiveData()
        return loginLiveData as MutableLiveData<Boolean>
    }

    override fun getHelpLiveData(): LiveData<Boolean> {
        if (helpLiveData == null)
            helpLiveData = MutableLiveData()
        return helpLiveData as MutableLiveData<Boolean>
    }

    /**
     * Called when help ImageView is clicked
     */
    override fun onHelpClicked() {
        helpLiveData?.value = true
    }

    /**
     *
     * Called when login Button is clicked
     */
    override fun onLoginClicked() {
        authorize()
    }

    /**
     * checks and validate the user name and password provided by user.
     * If the user name and password is valid, then starts SelectDataSourceActivity.
     * If the user name and password is not valid then shows appropriate error message
     */
    private fun authorize() {
        val validUserName = isUserNameValid()
        val validPassword = isPasswordValid()

        showOrHideUserNameError(validUserName)
        showOrHidePasswordError(validPassword)
        if (isUserNameValid() && isPasswordValid()) {
            loginLiveData?.value = true
        }
    }

    /**
     * Shows or hides error message in password TextInputLayout
     * @param validPassword Whether the password is valid
     */
    private fun showOrHidePasswordError(validPassword: Boolean) {
        if (!validPassword)
            errorPassword.set(context.getString(R.string.error_invalid_user_name))
        else
            errorPassword.set("")
    }

    /**
     * Shows or hides error message in user name TextInputLayout
     * @param validUserName Whether the user name is valid
     */
    private fun showOrHideUserNameError(validUserName: Boolean) {
        if (!validUserName)
            errorUserName.set(context.getString(R.string.error_invalid_user_name))
        else
            errorUserName.set("")

    }

    /** Validates user name input by user
     * @return boolean depending on whether or not the user name input is valid
     */
    private fun isPasswordValid(): Boolean {
        return regexPassword.containsMatchIn(password.trim())
    }

    /** Validates password input by user
     * @return boolean depending on whether or not the password input is valid
     */
    private fun isUserNameValid(): Boolean {
        return regexUserName.matches(userName.trim())
    }
}