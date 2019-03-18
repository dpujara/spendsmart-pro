package com.pujara.dhaval.spendsmart.dashboard.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.model.FriendBalance
import kotlinx.android.synthetic.main.recycler_view_friend_list.view.*

class FriendListAdapter(private var friendList: MutableList<FriendBalance>) : RecyclerView.Adapter<FriendListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.recycler_view_friend_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.friend_name.text = friendList[position].name
        if(friendList[position].balanace == 0){
            holder.view.friend_settle_up.text = "Settled Up"
        }
    }

    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }
}