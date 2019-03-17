package com.pujara.dhaval.spendsmart.dashboard.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CustompagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {

    var mFragmentManager = fm
    var mFragmentItems: ArrayList<Fragment> = ArrayList()
    var mFragmentTitles:ArrayList<String> = ArrayList()


    fun addFragment(fragmentItem:Fragment,fragmentTitle:String){
        mFragmentItems.add(fragmentItem)
        mFragmentTitles.add(fragmentTitle)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentItems[position]
    }

    override fun getCount(): Int {
        return mFragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitles[position]
    }
}