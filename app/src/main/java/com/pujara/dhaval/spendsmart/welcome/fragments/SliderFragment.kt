package com.pujara.dhaval.spendsmart.welcome.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.util.Log.d
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.pujara.dhaval.spendsmart.NavigationHost
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.welcome.adapter.MpagerAdapter
import com.pujara.dhaval.spendsmart.welcome.adapter.TranslateClass
import kotlinx.android.synthetic.main.slider_fragment.view.*
import kotlinx.android.synthetic.main.smr_slide_forth.view.*

class SliderFragment : Fragment(){
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
        mPager.setAdapter(mpagerAdapter)

        dotslayout = view.findViewById(R.id.dotsLayout)
        createDots(0)

        view.viewpagerNextButton.setOnClickListener{nextSlide()}


        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                createDots(i)
                if (i == 3) {
                    view.viewpagerNextButton.visibility = View.VISIBLE
                    view.viewpagerNextButton.text = "Start"
                    loadMainPage()
                }else{
                    view.viewpagerNextButton.visibility = View.INVISIBLE
                }
            }
            override fun onPageScrollStateChanged(i: Int) {}
        })

        view.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_BACK){
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

    fun nextSlide() {
        val next_slide = mPager.currentItem + 1
        Log.d(TAG, "onClick: here")
        if (next_slide < 4) {
            mPager.currentItem = next_slide
        } else {
            loadMainPage()
        }
    }

    fun loadMainPage() {
        Log.d(TAG, "loadMainPage: Yahoo... I did it")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }
}