package com.pujara. dhaval.spendsmart.welcome.presenter.login

import com.google.firebase.auth.FirebaseAuth
import com.pujara.dhaval.spendsmart.welcome.model.LoginSignup
import com.pujara.dhaval.spendsmart.welcome.model.User
import com.pujara.dhaval.spendsmart.welcome.view.ILoginView

class LoginPresenter(private var iLoginView: ILoginView):
    ILoginPresenter {
    override fun onForgotPasswordClicked() {
        iLoginView.navigateToForgotFragment()
    }

    private var mFirebaseAuth = FirebaseAuth.getInstance()
    override fun onBackButtonClicked() {
        iLoginView.navigateToWelcomeFragment()
    }

    override fun onLogin(email: String, password: String) {
        val user = User(email,password);
        val isUsernameEmpty = user.isUsernameEmpty
        val isUsernameValid = user.isUsernameValid
        val isPasswordLengthValid = user.isPasswordLengthValid

        if(isUsernameEmpty){
            iLoginView.onLoginResult("Username can not be empty !!!")
            return
        }
        if (isUsernameValid){
            iLoginView.onLoginResult("Invalid Username")
            return
        }
        if (isPasswordLengthValid){
            iLoginView.onLoginResult("Invalid Password !!!")
            return
        }

        val iLoginSIgnupModel = LoginSignup()
        iLoginView.disableInput()
        iLoginView.showProgress()
        iLoginSIgnupModel.doSignIn(email,password,iLoginView)
    }
}