package com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom

interface IWelcomeBottomPresenter {
    fun onBackNavigationClicked()
    fun onSendFeedbackClicked(email: String, subject: String, description: String)
}