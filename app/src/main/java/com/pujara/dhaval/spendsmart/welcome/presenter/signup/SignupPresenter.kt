package com.pujara.dhaval.spendsmart.welcome.presenter.signup

import android.util.Log
import android.util.Log.d
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.Interactor.LoginSignupInteractor
import com.pujara.dhaval.spendsmart.welcome.model.LoginSignup
import com.pujara.dhaval.spendsmart.welcome.model.User
import com.pujara.dhaval.spendsmart.welcome.view.ISignupView

class SignupPresenter(internal var iSignupView: ISignupView):
    ISignupPresenter {
    private var mFirebaseAuth = FirebaseAuth.getInstance()
    override fun registerNewUser(name:String, email: String, password: String) {

        val user = User(email,password);
        user.name = name

        val isNameEmpty = user.isNameEmpty

        if(isNameEmpty){
            displayError("Name can not be empty !!!")
            return
        }

        val isUsernameEmpty = user.isUsernameEmpty
        if(isUsernameEmpty){
            displayError("Username can not be empty !!!")
            return
        }

        val isUsernameValid = user.isUsernameValid
        if (isUsernameValid){
            displayError("Invalid Emailid format !!!")
            return
        }

        val isPasswordLengthValid = user.isPasswordLengthValid

        if (isPasswordLengthValid){
            displayError("Invalid Password !!!")
            return
        }

        val iLoginSIgnupModel = LoginSignup()
        iSignupView.disableInput()
        iSignupView.showProgress()
        iLoginSIgnupModel.doSignup(email,password)
        val userCurrent : FirebaseUser? = mFirebaseAuth.currentUser
        d ("Tag",userCurrent.toString())
    }

    private fun displayError(message:String) {
        iSignupView.onSignUpResult(message)
    }

    override fun onBackButtonClicked() {
        iSignupView.navigateToWelcomeFragment()
    }

    override fun displayEdittextDrawable(str : CharSequence,editText: EditText){
        if (str.isNotEmpty()){
            iSignupView.setEditTextDrawable(R.drawable.ic_cancel,editText)
        }else{
            iSignupView.setEditTextDrawable(0,editText)
        }
    }

    override fun emptyNameEdittext(editText: EditText){
        iSignupView.setEditTextDrawable(0,editText)
        iSignupView.emptyEdittext(editText)
    }

    override fun emptyEmailEdittext(editText: EditText){
        iSignupView.setEditTextDrawable(0,editText)
        iSignupView.emptyEdittext(editText)
    }
}