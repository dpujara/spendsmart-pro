package com.pujara.dhaval.spendsmart.dashboard.view

import com.pujara.dhaval.spendsmart.dashboard.model.PersonalExpenseData

interface IPersonalExpenseView {
    fun setExpenseList(expenseList: ArrayList<PersonalExpenseData>)
}