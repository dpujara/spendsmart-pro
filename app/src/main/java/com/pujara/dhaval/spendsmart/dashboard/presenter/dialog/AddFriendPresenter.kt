package com.pujara.dhaval.spendsmart.dashboard.presenter.dialog


import com.pujara.dhaval.spendsmart.dashboard.interactor.DashboardInteractor
import com.pujara.dhaval.spendsmart.dashboard.view.IAddFriendView

class AddFriendPresenter(private var iAddFriendView: IAddFriendView) : IAddFriendPresenter {
    override fun addFriend(email: String, user: String?, name: String?) {
        val dashboardInteractor = DashboardInteractor()
        dashboardInteractor.AddFriendsFirebase(iAddFriendView,email,user,name)
    }
}