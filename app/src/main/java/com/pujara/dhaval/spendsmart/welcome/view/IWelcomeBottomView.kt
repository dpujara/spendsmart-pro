package com.pujara.dhaval.spendsmart.welcome.view

interface IWelcomeBottomView {
    fun onBackNavigationClicked()
    fun displayError(error:String)
    fun onFeedbackResult(result: Boolean)
}