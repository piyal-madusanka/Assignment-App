package com.max119.splashapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    private val handler=Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    private val runnable= Runnable {
        if(!isFinishing){
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}