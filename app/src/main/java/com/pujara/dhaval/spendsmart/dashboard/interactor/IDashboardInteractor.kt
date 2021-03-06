package com.pujara.dhaval.spendsmart.dashboard.interactor

import com.pujara.dhaval.spendsmart.dashboard.view.*

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

    fun AddPersonalExpenseFirebase(
        iAddExpenseView: IAddExpenseView,
        descr: String,
        amountExpense: String,
        date: String,
        expense: String,
        user: String?
    )

    fun fetchPersonalExpenseFirebase(iPersonalExpenseView: IPersonalExpenseView, user: String?)
    fun UpdatePersonalExpenseFirebase(iAddExpenseView: IAddExpenseView, uniqueKey: String, descr: String, amount: String, date: String, expense: String, user: String?)
    fun deletePersonalExpenseFirebase(iAddExpenseView: IAddExpenseView, uniqueKey: String, user: String?)
    fun fetchFriendsFirebase(iCreateGroupView: ICreateGroupView, user: String?)
}