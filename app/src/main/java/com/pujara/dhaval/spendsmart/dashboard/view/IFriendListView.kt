package com.pujara.dhaval.spendsmart.dashboard.view

import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance

interface IFriendListView {
    fun setFriendList(friendList: MutableList<FriendBalance>)
}