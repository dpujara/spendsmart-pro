package com.pujara.dhaval.spendsmart.welcome.model

import com.pujara.dhaval.spendsmart.welcome.view.*

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

    fun submitFeedback(
        email: String,
        subject: String,
        description: String,
        iWelcomeBottomView1: IWelcomeBottomView
    )
}