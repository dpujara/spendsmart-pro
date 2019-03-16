package com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom

import com.pujara.dhaval.spendsmart.welcome.model.LoginSignup
import com.pujara.dhaval.spendsmart.welcome.model.User
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView

class WelcomeBottomPresenter(internal var iWelcomeBottomView: IWelcomeBottomView) : IWelcomeBottomPresenter {
    override fun onSendFeedbackClicked(email: String, subject: String, description: String) {
        val user = User(email,"");
        val isUsernameEmpty = user.isUsernameEmpty

        if(isUsernameEmpty){
            displayError("Email can not be empty !!!")
            return
        }

        val isUsernameValid = user.isUsernameValid
        if (isUsernameValid){
            displayError("Invalid Emailid format !!!")
            return
        }

        user.stringValidation = subject.trim()
        val isSubjectEmpty = user.isStringEmpty
        if(isSubjectEmpty){
            displayError("Subject can not be empty !!!")
            return
        }


        user.stringValidation = description.trim()
        val isDescrEmpty = user.isStringEmpty

        if(isDescrEmpty){
            displayError("Description can not be empty !!!")
            return
        }

        val iLoginSIgnupModel = LoginSignup()
        iLoginSIgnupModel.submitFeedback(email,subject,description,iWelcomeBottomView)
    }

    override fun onBackNavigationClicked() {
        iWelcomeBottomView.onBackNavigationClicked()
    }

    private fun displayError(message:String) {
        iWelcomeBottomView.displayError(message)
    }
}