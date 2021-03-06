package com.pujara.dhaval.spendsmart.welcome.Interactor

import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView

interface ILoginSignupInteractor{
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

    fun submitFeedback(email: String, subject: String, description: String, iWelcomeBottomView1: IWelcomeBottomView)
}