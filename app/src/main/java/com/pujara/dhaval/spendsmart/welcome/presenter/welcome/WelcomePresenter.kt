package com.pujara.dhaval.spendsmart.welcome.presenter.welcome

import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeView

class WelcomePresenter(internal var iWelcomeView: IWelcomeView):
    IWelcomePresenter {
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