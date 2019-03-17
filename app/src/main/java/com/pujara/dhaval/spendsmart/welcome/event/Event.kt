package com.pujara.dhaval.spendsmart.welcome.event

import com.google.firebase.auth.FirebaseUser
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView
import java.lang.Exception

class Event:IEvent {
    override fun onFeedbackResult(iWelcomeBottomView: IWelcomeBottomView, result: Boolean) {
        iWelcomeBottomView.onFeedbackResult(result)
    }

    override fun onSignUpSuccess(
        user: String?,
        iSignupView: ISignupView
    ) {
        iSignupView.onSignUpResult("Successfully Signed up !!!")
    }

    override fun onSignUpFailure(
        exception: Exception?,
        iSignupView: ISignupView
    ) {
        if (exception != null) {
            exception.message?.let { iSignupView.onSignUpError(it) }
        }
    }

    override fun onSignInSuccess(user: FirebaseUser?,iLoginView: ILoginView) {
        iLoginView.onSignInSuccess(user)
    }

    override fun onSignInFailure(iLoginView: ILoginView, exception: String?) {
        iLoginView.onSignInFailure(exception)
    }

    override fun onForgotPasswordSuccess(message: String,iForgotpasswordView: IForgotpasswordView){
        iForgotpasswordView.onPasswordSuccess(message)
    }
    override fun onForgotPasswordFailure(message: String, iForgotpasswordView: IForgotpasswordView){
        iForgotpasswordView.onPasswordFailure(message)
    }
}