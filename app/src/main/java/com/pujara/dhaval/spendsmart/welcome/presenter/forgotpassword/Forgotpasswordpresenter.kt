package com.pujara.dhaval.spendsmart.welcome.presenter.forgotpassword

import com.pujara.dhaval.spendsmart.welcome.model.LoginSignup
import com.pujara.dhaval.spendsmart.welcome.model.User
import com.pujara.dhaval.spendsmart.welcome.view.IForgotpasswordView

class Forgotpasswordpresenter(private var iForgotpasswordView: IForgotpasswordView): IForgotpasswordPresenter{
    override fun onBackNavigationClicked() {
        iForgotpasswordView.onBackNavigationClicked()
    }

    override fun onResetButtonClick(email: String) {
        val user = User(email,"")
        val isUsernameEmpty = user.isUsernameEmpty
        if(isUsernameEmpty){
            iForgotpasswordView.onForgotResult("Emailid can not be empty !!!")
            return
        }

        val isUsernameValid = user.isUsernameValid

        if (isUsernameValid){
            iForgotpasswordView.onForgotResult("Invalid Email id")
            return
        }

        val iLoginSIgnupModel = LoginSignup()
        iLoginSIgnupModel.forgotPassword(email,iForgotpasswordView)
    }
}