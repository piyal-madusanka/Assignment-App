package com.max119.splashapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class Maincontent : BaceActivity() {
   var drawablelayout:DrawerLayout?=null
    var toggle:ActionBarDrawerToggle?=null
    private var mAuth: FirebaseAuth? = null
    var myEmail:String?=null
    //var c:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maincontent)
        mAuth = FirebaseAuth.getInstance()
        drawablelayout=findViewById(R.id.drawer)

      var navview=findViewById<NavigationView>(R.id.nav_view)
        navview.setNavigationItemSelectedListener {
           when(it.itemId){
               R.id.item1->{
                   drawablelayout!!.closeDrawer(GravityCompat.START)
               }
               R.id.item2->{
                   drawablelayout!!.closeDrawer(GravityCompat.START)
               }
               R.id.item3->{
                   drawablelayout!!.closeDrawer(GravityCompat.START)
               } R.id.item4->{

               }
               R.id.item5->{
               logOut(this)

               }
           }
            true
        }

    }
    fun openDrawer(view:View){
        drawablelayout!!.openDrawer(GravityCompat.START)
    }
    fun closeDrawer(view: View){
        drawablelayout!!.closeDrawer(GravityCompat.START)
    }
    fun logOut(activiy:Activity){
        val builder = AlertDialog.Builder(activiy)
        //set title for alert dialog
        builder.setTitle("Log Out")
        //set message for alert dialog
        builder.setMessage("Are tou sure you want to log out")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
            mAuth!!.signOut()
            openActivity(LoginActivity::class.java)
            finish()

        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
            drawablelayout!!.closeDrawer(GravityCompat.START)
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }


}