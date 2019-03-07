package com.pujara.dhaval.spendsmart.welcome.presenter.signup

import android.widget.EditText

interface ISignupPresenter {
    fun onBackButtonClicked()
    fun registerNewUser(name:String,email:String,password:String)
    fun displayEdittextDrawable(str:CharSequence,editText: EditText)
    fun emptyNameEdittext(editText: EditText)
    fun emptyEmailEdittext(editText: EditText)
}