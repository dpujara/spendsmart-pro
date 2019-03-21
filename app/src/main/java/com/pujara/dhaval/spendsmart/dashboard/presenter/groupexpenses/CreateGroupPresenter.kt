package com.pujara.dhaval.spendsmart.dashboard.presenter.groupexpenses

import android.text.TextUtils
import com.pujara.dhaval.spendsmart.dashboard.interactor.DashboardInteractor
import com.pujara.dhaval.spendsmart.dashboard.view.ICreateGroupView

class CreateGroupPresenter (private var iCreateGroupView: ICreateGroupView) : ICreateGroupPresenter{
    override fun fetchFriends(user: String?) {
        val dashboardInteractor = DashboardInteractor()
        dashboardInteractor.fetchFriendsFirebase(iCreateGroupView,user)
    }

    override fun checkGroupName(toString: String) {
        if(TextUtils.isEmpty(toString)){
            iCreateGroupView.displayMessage("Group name can not be empty !!!")
            return
        }
    }
}

