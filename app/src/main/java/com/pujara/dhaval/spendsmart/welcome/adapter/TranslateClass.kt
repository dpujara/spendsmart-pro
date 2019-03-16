package com.pujara.dhaval.spendsmart.welcome.adapter

import android.support.v4.view.ViewPager
import android.view.View
import android.widget.TextView
import com.pujara.dhaval.spendsmart.R

class TranslateClass : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.getWidth()
        if (position >= -1 && position <= 1) {
            page.findViewById<TextView>(R.id.textView5)
                .setTranslationX((position.toDouble() * 0.5 * pageWidth.toDouble()).toFloat())
            page.findViewById<TextView>(R.id.textView8)
                .setTranslationX((position.toDouble() * 1.25 * pageWidth.toDouble()).toFloat())
        } else {
            page.setAlpha(1f)
        }
    }
}
