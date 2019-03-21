package com.pujara.dhaval.spendsmart.dashboard.presenter.dialog

import android.text.TextUtils
import android.util.Log.d
import com.pujara.dhaval.spendsmart.dashboard.interactor.DashboardInteractor
import com.pujara.dhaval.spendsmart.dashboard.view.IAddExpenseView

class AddExpensePresenter(private var iAddExpenseView: IAddExpenseView) : IAddExpensePresenter {

    val dashboardInteractor = DashboardInteractor()

    override fun deleteData(uniqueKey: String, user: String?) {
        dashboardInteractor.deletePersonalExpenseFirebase(iAddExpenseView,uniqueKey,user)
    }

    override fun updateExpenseData(
        uniqueKey: String,
        descr: String,
        amount: String,
        date: String,
        expense: String,
        user: String?
    ) {
        dashboardInteractor.UpdatePersonalExpenseFirebase(
            iAddExpenseView,
            uniqueKey,
            descr,
            amount,
            date,
            expense,
            user
        )
    }

    var amountExpense: String = ""
    override fun insertExpenseData(descr: String, amount: String, date: String, expense: String, user: String?) {
        if (TextUtils.isEmpty(descr)) {
            displayErrorMessage("Enter Description !!!")
            return
        }
        if (TextUtils.isEmpty(amount)) {
            displayErrorMessage("Enter amount !!!")
            return
        }
        d("testing", date)
        if (TextUtils.isEmpty(date)) {
            displayErrorMessage("Enter Date !!!")
            return
        }
        dashboardInteractor.AddPersonalExpenseFirebase(iAddExpenseView, descr, amount, date, expense, user)
    }

    private fun displayErrorMessage(str: String) {
        iAddExpenseView.displayMessage(str)
    }
}