package com.pujara.dhaval.spendsmart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.pujara.dhaval.spendsmart.dashboard.DashboardActivity

class SplashActivity : AppCompatActivity() {

    private var mFirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainLooperHandler = Handler(Looper.getMainLooper())

        val user : String? = mFirebaseAuth.currentUser?.uid

        if(user == null){
            mainLooperHandler.postDelayed({
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 750)
        }else{
            mainLooperHandler.postDelayed({
                val intent = Intent(applicationContext, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }, 750)
        }
    }
}