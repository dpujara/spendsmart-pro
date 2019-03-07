package com.pujara.dhaval.spendsmart.welcome.event

import com.google.firebase.auth.FirebaseUser
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView
import java.lang.Exception

class Event:IEvent {

    override fun onSignUpSuccess(user: FirebaseUser?) {
    }

    override fun onSignUpFailure(exception: Exception?) {
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