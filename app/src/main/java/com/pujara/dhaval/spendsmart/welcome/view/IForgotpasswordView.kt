package com.pujara.dhaval.spendsmart.welcome.view

interface IForgotpasswordView {
    fun onForgotResult(message: String)
    fun onPasswordSuccess(message: String)
    fun onPasswordFailure(message: String)
    fun onBackNavigationClicked()
}
