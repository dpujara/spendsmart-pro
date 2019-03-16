package com.pujara.dhaval.spendsmart.welcome.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pujara.dhaval.spendsmart.R

class MpagerAdapter : PagerAdapter() {
    private val layouts = intArrayOf(
        R.layout.smr_slide_first,
        R.layout.smr_slide_second,
        R.layout.smr_slide_third,
        R.layout.smr_slide_forth
    )
    private var layoutInflater: LayoutInflater? = null

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        layoutInflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val view = layoutInflater?.inflate(layouts[position], container, false)
        container.addView(view)
        return view!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}