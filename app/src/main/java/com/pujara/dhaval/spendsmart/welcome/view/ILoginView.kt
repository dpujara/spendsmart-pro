package com.pujara.dhaval.spendsmart.welcome.view

import com.google.firebase.auth.FirebaseUser

interface ILoginView {
    fun onLoginResult(message:String)
    fun navigateToWelcomeFragment()
    fun onLoginSuccess()
    fun showProgress()
    fun hideProgress()
    fun disableInput()
    fun enableInput()
    fun onSignInSuccess(user: FirebaseUser?)
    fun onSignInFailure(exception: String?)
    fun navigateToForgotFragment()
}