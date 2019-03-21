package com.pujara.dhaval.spendsmart.dashboard.view

import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance

interface ICreateGroupView {
    fun displayMessage(str: String)
    fun setFriendList(friendList: MutableList<FriendBalance>)
}
