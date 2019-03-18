package com.pujara.dhaval.spendsmart.dashboard.presenter.friendlist

import com.pujara.dhaval.spendsmart.dashboard.interactor.DashboardInteractor
import com.pujara.dhaval.spendsmart.dashboard.view.IFriendListView

class FriendListPresenter(private var iFriendListView: IFriendListView) : IFriendListPresenter {
    override fun fetchFriends(user: String?) {
        val dashboardInteractor = DashboardInteractor()
        dashboardInteractor.fetchFriendsFirebase(iFriendListView,user)
    }
}