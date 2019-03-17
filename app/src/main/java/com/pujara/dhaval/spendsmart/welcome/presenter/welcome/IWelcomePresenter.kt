package com.pujara.dhaval.spendsmart.welcome.presenter.welcome

import android.support.v4.app.FragmentActivity

interface IWelcomePresenter {
    fun onLoginButtonClicked()
    fun onSignUpButtonClicked()
    fun onTermTextviewClicked()
    fun onPolicyTextviewClicked()
    fun onContactUsTextviewClicked()
    fun onGoogleSignInClicked(activity: FragmentActivity?)
}