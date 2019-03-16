package com.pujara.dhaval.spendsmart.welcome.model

import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView

interface ILoginSignup {
    fun checkSession()
    fun doSignup(
        email: String,
        password: String,
        name: String,
        iSignupView: ISignupView
    )
    fun doSignIn(
        email: String,
        password: String,
        iLoginView: ILoginView
    )

    fun forgotPassword(
        email: String,
        iForgotpasswordView: IForgotpasswordView
    )
}