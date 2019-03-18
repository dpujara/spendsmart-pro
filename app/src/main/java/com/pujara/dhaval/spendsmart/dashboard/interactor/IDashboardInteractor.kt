package com.pujara.dhaval.spendsmart.dashboard.interactor

import com.pujara.dhaval.spendsmart.dashboard.view.IAddFriendView
import com.pujara.dhaval.spendsmart.dashboard.view.IFriendListView

interface IDashboardInteractor {
    fun fetchFriendsFirebase(
        iFriendListView: IFriendListView?,
        user: String?
    )

    fun AddFriendsFirebase(
        iAddFriendView: IAddFriendView,
        email: String,
        user: String?,
        name: String?
    )
}