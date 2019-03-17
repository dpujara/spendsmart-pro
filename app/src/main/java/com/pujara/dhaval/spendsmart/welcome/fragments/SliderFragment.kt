package com.pujara.dhaval.spendsmart.welcome.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.DashboardActivity
import com.pujara.dhaval.spendsmart.welcome.adapter.MpagerAdapter
import com.pujara.dhaval.spendsmart.welcome.adapter.TranslateClass
import kotlinx.android.synthetic.main.slider_fragment.view.*


class SliderFragment : Fragment() {
    private lateinit var mPager: ViewPager
    private lateinit var mpagerAdapter: MpagerAdapter
    private val TAG = "SliderFragment"
    private lateinit var dotslayout: LinearLayout
    private var dots = arrayOfNulls<ImageView>(4)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.slider_fragment, container, false)
        mPager = view.findViewById(R.id.viewPager)
        mPager.setPageTransformer(false, TranslateClass())
        mpagerAdapter = MpagerAdapter()
        mPager.adapter = mpagerAdapter

        dotslayout = view.findViewById(R.id.dotsLayout)
        createDots(0)

        view.viewpagerNextButton.setOnClickListener { nextSlide() }


        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(i: Int) {
                createDots(i)
                if (i == 3) {
                    view.viewpagerNextButton.visibility = View.VISIBLE
                    view.viewpagerNextButton.text = "Start"
                } else {
                    view.viewpagerNextButton.visibility = View.INVISIBLE
                }
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })

        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    fragmentManager?.popBackStack()
                    (activity as NavigationHost).backPressed()
                }
                return false
            }
        })

        return view
    }

    fun createDots(currentPosition: Int) {
        dotslayout.removeAllViews()
        for (i in 0..3) {
            dots[i] = ImageView(activity)
            if (i == currentPosition) {
                dots[i]?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.active_dot))
            } else {
                dots[i]?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.inactive_dot))
            }
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 16, 8, 0)
            dotslayout.addView(dots[i], params)
        }
    }

    private fun nextSlide() {
        val nextSlide = mPager.currentItem + 1
        Log.d(TAG, "onClick: here")
        if (nextSlide < 4) {
            mPager.currentItem = nextSlide
        } else {
            loadMainPage()
        }
    }

    fun loadMainPage() {
        activity?.fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        (activity as NavigationHost).newActivity(DashboardActivity())
    }
}