package com.pujara.dhaval.spendsmart.dashboard.view

interface IAddFriendView {
    fun alreadyAdded(name: String?, email: String?)
    fun error(error: String)
    fun added(email: String)
}