package com.pujara.dhaval.spendsmart.dashboard.presenter.personalexpense

import com.pujara.dhaval.spendsmart.dashboard.interactor.DashboardInteractor
import com.pujara.dhaval.spendsmart.dashboard.view.IPersonalExpenseView

class PersonalExpensePresenter (private var iPersonalExpenseView: IPersonalExpenseView): IPersonalExpensePresenter{
    override fun fetchExpense(user: String?) {
        val dashboardInteractor = DashboardInteractor()
        dashboardInteractor.fetchPersonalExpenseFirebase(iPersonalExpenseView,user)
    }
}