package com.pujara.dhaval.spendsmart

import android.support.v4.app.Fragment

interface NavigationHost {
    /**
     * Trigger a navigation to the specified fragment, optionally adding a transaction to the back
     * stack to make this navigation reversible.
     */
    fun backPressed()
    fun navigateTo(
        fragment: Fragment,
        addToBackStack: Boolean,
        animation: Boolean,
        animation1: Int,
        animation2: Int,
        animation3: Int,
        animation4: Int
    )
}