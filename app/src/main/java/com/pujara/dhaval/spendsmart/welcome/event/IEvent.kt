package com.pujara.dhaval.spendsmart.welcome.event

import com.google.firebase.auth.FirebaseUser
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView
import java.lang.Exception

interface IEvent {
    fun onSignUpSuccess(user: String?, iSignupView: ISignupView)
    fun onSignUpFailure(
        exception: Exception?,
        iSignupView: ISignupView
    )
    fun onSignInSuccess(
        user: FirebaseUser?,
        iLoginView: ILoginView
    )
    fun onSignInFailure(
        iLoginView: ILoginView,
        exception: String?
    )

    fun onForgotPasswordSuccess(message: String,iForgotpasswordView: IForgotpasswordView)
    fun onForgotPasswordFailure(message: String, iForgotpasswordView: IForgotpasswordView)
    fun onFeedbackResult(iWelcomeBottomView: IWelcomeBottomView, result: Boolean)
}