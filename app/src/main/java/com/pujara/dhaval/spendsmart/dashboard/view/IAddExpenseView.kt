package com.pujara.dhaval.spendsmart.dashboard.view

interface IAddExpenseView {
    fun displayMessage(str: String)
    fun expenseAdded(str: String)
    fun updatedData(message: String)
}