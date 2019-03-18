package com.pujara.dhaval.spendsmart.dashboard

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log.d
import android.view.View
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.adapter.CustompagerAdapter
import com.pujara.dhaval.spendsmart.dashboard.fragment.FriendList
import com.pujara.dhaval.spendsmart.dashboard.fragment.GroupExpense
import com.pujara.dhaval.spendsmart.dashboard.fragment.PersonalExpense
import kotlinx.android.synthetic.main.activity_dashboard.*
import com.pujara.dhaval.spendsmart.NavigationHost


class DashboardActivity : AppCompatActivity(),NavigationHost {
    override fun newActivity(dashboardActivity: Activity) {

    }

    override fun backPressed() {

    }

    private lateinit var drawer: DrawerLayout
    var pagerAdapter: CustompagerAdapter? = null
    var fabstate: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //Navigation Drawer
        val toolbar: Toolbar = findViewById(R.id.toolbar_drawer)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        //PagerAdapter
        pagerAdapter = CustompagerAdapter(supportFragmentManager)
        pagerAdapter?.addFragment(PersonalExpense(), "Personal")
        pagerAdapter?.addFragment(FriendList(), "Friends")
        pagerAdapter?.addFragment(GroupExpense(), "Group")

        viewpager_drawer.adapter = pagerAdapter
        tablayout_drawer.setupWithViewPager(viewpager_drawer)


        fabBtn.setOnClickListener {
            if (fabstate == 0) {
                val persoanlFragment = PersonalExpense()
                persoanlFragment.demo()
            } else if (fabstate == 1) {
                val friendList = FriendList()
                friendList.addFriends(this)
            } else if (fabstate == 2) {
                val groupExpense = GroupExpense()
                groupExpense.demo()
            }
        }

        viewpager_drawer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(p0: Int) {
                if (p0 == 0) {
                    fabBtn.setImageResource(R.drawable.ic_person_add_black_24dp)
                    fabstate = 0
                } else if (p0 == 1) {
                    fabBtn.setImageResource(R.drawable.ic_action_add)
                    fabstate = 1
                } else if (p0 == 2) {
                    fabBtn.setImageResource(com.pujara.dhaval.spendsmart.R.drawable.ic_group_add_black_24dp)
                    fabstate = 2
                }
            }
        })
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        d("FriendList","dude")
    }

    override fun onStop() {
        super.onStop()
        d("FriendList","dude")
    }

    override fun onPause() {
        super.onPause()
        d("FriendList","dude")
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
        transaction.replace(R.id.container_drawer, fragment)
        if (addToBackStack) {
            d("Tag","Hello Added to backstack")
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}