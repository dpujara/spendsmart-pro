package com.pujara.dhaval.spendsmart.dashboard.fragment


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pujara.dhaval.spendsmart.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class GroupExpense : Fragment() {
    private var fabBtn: FloatingActionButton? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_group_expense, container, false)
        fabBtn = view.findViewById(R.id.fabBtn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(!isAdded){
            return
        }
        fabBtn?.setOnClickListener {
            val fragment : Fragment? = fragmentManager?.findFragmentById(R.id.fragment_container_group)
            if (fragment is FriendList){
                Log.d("fab", "fab Group")
            }
        }
    }

    fun demo(){
        Log.d("fab", "group")
    }
}
