package com.pujara.dhaval.spendsmart.welcome.view

import android.widget.EditText

interface ISignupView {
    fun navigateToWelcomeFragment()
    fun onSignUpResult(message:String)
    fun showProgress()
    fun hideProgress()
    fun enableInput()
    fun disableInput()
    fun setEditTextDrawable(drawable:Int,editText: EditText)
    fun emptyEdittext(editText: EditText)
}