package com.pujara.dhaval.spendsmart.welcome.presenter.welcome

import android.support.v4.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.pujara.dhaval.spendsmart.welcome.model.LoginSignup
import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeView

class WelcomePresenter(private var iWelcomeView: IWelcomeView):
    IWelcomePresenter {
    override fun onGoogleSignInClicked(activity: FragmentActivity?) {

    }

    override fun onTermTextviewClicked() {
        iWelcomeView.navigateToTermsFragment()
    }

    override fun onPolicyTextviewClicked() {
        iWelcomeView.navigateToPolicyFragment()
    }

    override fun onContactUsTextviewClicked() {
        iWelcomeView.navigateToContactUsFragment()
    }

    override fun onLoginButtonClicked() {
        iWelcomeView.navigateToLoginFragment()
    }

    override fun onSignUpButtonClicked() {
        iWelcomeView.navigateToSignUpFragment()
    }
}