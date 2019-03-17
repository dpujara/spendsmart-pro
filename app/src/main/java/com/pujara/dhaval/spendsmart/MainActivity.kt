package com.pujara.dhaval.spendsmart

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log.d
import com.pujara.dhaval.spendsmart.welcome.fragments.WelcomeFragment

class MainActivity : AppCompatActivity(), NavigationHost {
    override fun newActivity(dashboardActivity: Activity) {
        val intent = Intent(this,dashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, WelcomeFragment())
                .commit()
        }
    }

    override fun backPressed() {
        onBackPressed()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
    }

    override fun navigateTo(
        fragment: Fragment,
        addToBackStack: Boolean,
        animation: Boolean,
        animation1: Int,
        animation2: Int,
        animation3: Int,
        animation4: Int
    ) {
        val transaction = supportFragmentManager
            .beginTransaction()
        if (animation) {
            transaction.setCustomAnimations(animation1, animation2, animation3, animation4)
        }
        transaction.replace(R.id.container, fragment)
        if (addToBackStack) {
            d("Tag","Hello Added to backstack")
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}
