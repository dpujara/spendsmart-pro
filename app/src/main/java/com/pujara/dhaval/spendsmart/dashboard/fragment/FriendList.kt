package com.pujara.dhaval.spendsmart.dashboard.fragment


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.DashboardActivity
import com.pujara.dhaval.spendsmart.dashboard.adapter.FriendListAdapter
import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance
import com.pujara.dhaval.spendsmart.dashboard.presenter.friendlist.FriendListPresenter
import com.pujara.dhaval.spendsmart.dashboard.presenter.friendlist.IFriendListPresenter
import com.pujara.dhaval.spendsmart.dashboard.view.IFriendListView


class FriendList : Fragment(), IFriendListView {
    private lateinit var friendListData: MutableList<FriendBalance>
    private var fabBtn: FloatingActionButton? = null

    override fun setFriendList(friendList: MutableList<FriendBalance>) {
        friendListData = friendList
        recyclerViewFriendList = view?.findViewById(R.id.recycler_view_friend_list) as RecyclerView
        recyclerViewFriendList?.layoutManager = LinearLayoutManager(activity)
        recyclerViewFriendList?.adapter = FriendListAdapter(friendListData)
    }

    private var mFirebaseAuth = FirebaseAuth.getInstance()
    val user: String? = mFirebaseAuth.currentUser?.uid

    private var recyclerViewFriendList: RecyclerView? = null
    private lateinit var friendListPresenter: IFriendListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        d("FriendList", "Pause")
        val view = inflater.inflate(R.layout.fragment_friend_list, container, false)
        friendListPresenter = FriendListPresenter(this)
        friendListPresenter.fetchFriends(user)
        fabBtn = view.findViewById(R.id.fabBtn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isAdded) {
            return
        }
        d("FriendList", "Pause")
        fabBtn?.setOnClickListener {
            val fragment: Fragment? = fragmentManager?.findFragmentById(R.id.fragment_container_friend)
            if (fragment is FriendList) {
                d("fab", "fab friend")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        d("FriendList", "Pause")
    }

    override fun onResume() {
        super.onResume()
        d("FriendList", "Resume")
    }

    fun addFriends(dashboardActivity: DashboardActivity) {
        (dashboardActivity as NavigationHost).navigateTo(
            FullscreenDialog(),
            true,
            true,
            R.anim.slide_in_bottom,
            R.anim.fade_out,
            0,
            0
        )
    }
}
