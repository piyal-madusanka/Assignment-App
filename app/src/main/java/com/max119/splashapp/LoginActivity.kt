package com.max119.splashapp

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaceActivity() {
    var mProgress: ProgressDialog? = null
    var runnable: Runnable? = null
    private val handler = Handler()
    private var mAuth: FirebaseAuth? = null
    var sp: SharedPreferences? =null
    var mailEditText:EditText?=null
    var passEditText:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        mProgress = ProgressDialog(this);
        mProgress!!.setTitle("Processing...");
        mProgress!!.setMessage("Please wait...");
        mProgress!!.setCancelable(false);
        mProgress!!.setIndeterminate(true);
    }

    fun goToMainView(view: View) {

        mailEditText=findViewById<EditText>(R.id.emailText)
        passEditText= findViewById<EditText>(R.id.pastext)
        var password = passEditText!!.text.toString()
        var email = mailEditText!!.text.toString()
        if (mailValidation(email, mailEditText!!) && passwordValidator(password, passEditText!!)) {
            sp=applicationContext.getSharedPreferences("Myuser", MODE_PRIVATE)
            var shmail: String? =sp!!.getString("email","")
            var shpass:String?=sp!!.getString("pass","")
           mProgress!!.show();
            if(email == shmail && password == shpass){
                openActivity(Maincontent::class.java)
                finish()
                mProgress!!.dismiss()
            }else {
                signin(email, password)
            }
        }
    }

    fun goToRegisterView(view: View) {
        openActivity(RegisterActivity::class.java)
    }

    fun signin(email: String, password: String){

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Authentication succeeded !.", Toast.LENGTH_SHORT).show()

                     if (!isFinishing) {
                           try {
                               sp=getSharedPreferences("Myuser", MODE_PRIVATE)
                               val editor:SharedPreferences.Editor=sp!!.edit()
                               editor.putString("email",email)
                               editor.putString("pass",password)
                               editor.apply()
                               openActivity(Maincontent::class.java)
                               finish()
                              mProgress!!.dismiss()
                           }catch (ex:Exception){

                           }
                        }


                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    mProgress!!.dismiss()
                }

                // ...
            }
    }
    fun loadMain(){
        var currentUser= mAuth!!.currentUser!!
        if(currentUser!=null) {
            try {
                openActivity(Maincontent::class.java)
            }catch (ex:Exception){

            }

        }

    }
    override fun onStart() {
        super.onStart()
        try {
          loadMain()
        }catch (ex:Exception){

        }

    }

}