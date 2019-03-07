package com.pujara.dhaval.spendsmart.welcome.presenter.welcomebottom

import com.pujara.dhaval.spendsmart.welcome.view.IWelcomeBottomView

class WelcomeBottomPresenter(internal var iWelcomeBottomView: IWelcomeBottomView) : IWelcomeBottomPresenter {
    override fun onBackNavigationClicked() {
        iWelcomeBottomView.onBackNavigationClicked()
    }
}