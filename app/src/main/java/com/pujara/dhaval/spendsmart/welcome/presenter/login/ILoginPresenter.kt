package com.pujara.dhaval.spendsmart.welcome.presenter.login

interface ILoginPresenter {
    fun onLogin(email:String,password:String)
    fun onBackButtonClicked()
    fun onForgotPasswordClicked()
}