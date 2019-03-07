package com.pujara.dhaval.spendsmart.welcome.model

import com.pujara.dhaval.spendsmart.welcome.Interactor.ILoginSignupInteractor
import com.pujara.dhaval.spendsmart.welcome.Interactor.LoginSignupInteractor
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView

class LoginSignup : ILoginSignup , ILoginSignupInteractor{
    var loginSignupInteractor = LoginSignupInteractor()

    override fun forgotPassword(
        email: String,
        iForgotpasswordView: IForgotpasswordView
    ) {
        loginSignupInteractor.forgotPassword(email, iForgotpasswordView)
    }

    override fun checkSession() {

    }

    override fun doSignup(email: String, password: String) {
        loginSignupInteractor.doSignup(email,password)
    }

    override fun doSignIn(
        email: String,
        password: String,
        iLoginView: ILoginView
    ) {
        loginSignupInteractor.doSignIn(email, password, iLoginView)
    }
}