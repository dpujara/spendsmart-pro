package com.pujara.dhaval.spendsmart.dashboard.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.ButtonChangeVisibility
import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance
import com.pujara.dhaval.spendsmart.dashboard.presenter.groupexpenses.CreateGroupPresenter
import com.pujara.dhaval.spendsmart.dashboard.presenter.groupexpenses.ICreateGroupPresenter
import com.pujara.dhaval.spendsmart.dashboard.view.ICreateGroupView
import kotlinx.android.synthetic.main.full_screen_dialog_create_group_body.view.*
import com.pujara.dhaval.spendsmart.dashboard.adapter.FriendListAdapterGroup
import com.pujara.dhaval.spendsmart.dashboard.adapter.FriendListAdapterGroups
import kotlinx.android.synthetic.main.full_screen_dialog_create_group.view.*


class FullScreenDialogGroup : DialogFragment(), ICreateGroupView, FriendListAdapterGroups.onFriendListerner {
    override fun onFriendClick(position: Int) {
        if (selectedFriends.contains(position)) {
            selectedFriends.remove(position);
        } else {
            selectedFriends.add(position);
        }
        d("clicked",selectedFriends.toString())
    }

    override fun setFriendList(friendList: MutableList<FriendBalance>) {
        friendListData = friendList as ArrayList<FriendBalance>
        recyclerViewFriendList = view?.findViewById(R.id.recycler_view_friend_list_group) as RecyclerView
        recyclerViewFriendList?.layoutManager = LinearLayoutManager(activity)
        recyclerViewFriendList?.adapter = FriendListAdapterGroups(friendListData, this, context)
    }

    private var recyclerViewFriendList: RecyclerView? = null
    val selectedFriends = mutableSetOf<Int>()
    private lateinit var fullScreenDialogGroup: ButtonChangeVisibility
    private lateinit var iCreateGroupPresenter: ICreateGroupPresenter
    private var snackbar: Snackbar? = null
    private var mFirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var friendListData: ArrayList<FriendBalance>
    val user: String? = mFirebaseAuth.currentUser?.uid
    private lateinit var listView: ListView

    var groupType: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.full_screen_dialog_create_group, container, false)
        iCreateGroupPresenter = CreateGroupPresenter(this)
        iCreateGroupPresenter.fetchFriends(user)
        rootView.button_close_create_group.setOnClickListener {
            fullScreenDialogGroup.updateVisibility()
            view?.let { hideSoftKeyboard(it) }
            fragmentManager?.popBackStack()
            dismiss()
        }


        rootView.edittext_descr_fullscreen_dialog_personal.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    view?.textInputLayout?.hint = "Name"
                } else {
                    view?.textInputLayout?.hint = "Enter Group Name"
                }
            }

        rootView.textView_save_create_group.setOnClickListener {
            iCreateGroupPresenter.checkGroupName(view?.edittext_descr_fullscreen_dialog_personal?.text.toString())
        }

        rootView.textView4.setOnClickListener {
            groupType = context?.resources?.getString(R.string.apartment)
            changeColor(
                R.color.colorPrimary,
                R.color.colorSecondaryDark,
                R.color.colorSecondaryDark,
                R.color.colorSecondaryDark
            )
        }

        rootView.textView6.setOnClickListener {
            groupType = context?.resources?.getString(R.string.house)
            changeColor(
                R.color.colorSecondaryDark,
                R.color.colorPrimary,
                R.color.colorSecondaryDark,
                R.color.colorSecondaryDark
            )
        }

        rootView.textView7.setOnClickListener {
            groupType = context?.resources?.getString(R.string.trip)
            changeColor(
                R.color.colorSecondaryDark,
                R.color.colorSecondaryDark,
                R.color.colorPrimary,
                R.color.colorSecondaryDark
            )
        }

        rootView.textView9.setOnClickListener {
            groupType = context?.resources?.getString(R.string.others)
            changeColor(
                R.color.colorSecondaryDark,
                R.color.colorSecondaryDark,
                R.color.colorSecondaryDark,
                R.color.colorPrimary
            )
        }

        return rootView
    }

    fun changeColor(color1: Int, color2: Int, color3: Int, color4: Int) {
        context?.resources?.getColor(color1)?.let { it1 -> view?.textView4?.setBackgroundColor(it1) }
        context?.resources?.getColor(color2)?.let { it1 -> view?.textView6?.setBackgroundColor(it1) }
        context?.resources?.getColor(color3)?.let { it1 -> view?.textView7?.setBackgroundColor(it1) }
        context?.resources?.getColor(color4)?.let { it1 -> view?.textView9?.setBackgroundColor(it1) }
    }

    override fun displayMessage(str: String) {
        snackbar = view?.let { Snackbar.make(it, str, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fullScreenDialogGroup = activity as ButtonChangeVisibility
    }
}