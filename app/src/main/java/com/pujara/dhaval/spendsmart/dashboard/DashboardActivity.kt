package com.pujara.dhaval.spendsmart.dashboard

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.pujara.dhaval.spendsmart.R
import com.pujara.dhaval.spendsmart.dashboard.adapter.CustompagerAdapter
import com.pujara.dhaval.spendsmart.dashboard.fragment.FriendList
import com.pujara.dhaval.spendsmart.dashboard.fragment.GroupExpense
import com.pujara.dhaval.spendsmart.dashboard.fragment.PersonalExpense
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(){
    private lateinit var drawer : DrawerLayout
    var pagerAdapter:CustompagerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //Navigation Drawer
        val toolbar : Toolbar = findViewById(R.id.toolbar_drawer)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        //PagerAdapter
        pagerAdapter = CustompagerAdapter(supportFragmentManager)
        pagerAdapter?.addFragment(PersonalExpense(),"Personal")
        pagerAdapter?.addFragment(FriendList(),"Friends")
        pagerAdapter?.addFragment(GroupExpense(),"Group")

        viewpager_drawer.adapter = pagerAdapter
        tablayout_drawer.setupWithViewPager(viewpager_drawer)
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}