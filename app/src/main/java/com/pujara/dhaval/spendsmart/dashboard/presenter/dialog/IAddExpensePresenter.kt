package com.pujara.dhaval.spendsmart.dashboard.presenter.dialog

interface IAddExpensePresenter {
    fun insertExpenseData(descr: String, amount: String, date: String, expense: String,user: String?)
    fun updateExpenseData(uniqueKey: String, descr: String, amount: String, date: String, expense: String, user: String?)
    fun deleteData(uniqueKey: String, user: String?)
}