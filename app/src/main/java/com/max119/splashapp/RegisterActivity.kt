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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class RegisterActivity : BaceActivity(){

    var mProgress: ProgressDialog? = null
    var runnable: Runnable? = null
    private val handler = Handler()
    private lateinit var mAuth: FirebaseAuth
    private var database: FirebaseDatabase? =null
    private var myRef: DatabaseReference? =null
    var firstname:EditText?=null
    var lastname:EditText?=null
    var sp: SharedPreferences? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        database=FirebaseDatabase.getInstance();
        myRef=database!!.reference
        mAuth = FirebaseAuth.getInstance()
        mProgress = ProgressDialog(this);
        mProgress!!.setTitle("Processing...");
        mProgress!!.setMessage("Please wait...");
        mProgress!!.setCancelable(false);
        mProgress!!.setIndeterminate(true);
    }

    fun goBack(view: View) {
        finish()
    }
    fun goToMainView(view: View){
        mProgress!!.show();
        firstname=findViewById<EditText>(R.id.et_firstname)
        lastname=findViewById<EditText>(R.id.et_lastname)
        var email=findViewById<EditText>(R.id.et_email)
        var password=findViewById<EditText>(R.id.et_password)
        var password2=findViewById<EditText>(R.id.et_password2)

        if(validatenames(firstname!!,lastname!!) && mailValidation(email.text.toString(),email) && passwordValidator(password.text.toString(),password)
                && comparePaswords(password.text.toString(),password2.text.toString(),password2)){
            createAccount(email.text.toString(),password.text.toString())
        }else{

            mProgress!!.dismiss()
        }

    }

    fun createAccount(email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                sp=getSharedPreferences("Myuser", MODE_PRIVATE)
                val editor:SharedPreferences.Editor=sp!!.edit()
                editor.putString("email",email)
                editor.putString("pass",password)
                editor.apply()
                runnable = Runnable {
                    if (!isFinishing) {
                        myRef!!.child("Users").child(getSHA(email)!!).child("email").setValue(email)
                        myRef!!.child("Users").child(getSHA(email)!!).child("first_name").setValue(firstname!!.text.toString())
                        myRef!!.child("Users").child(getSHA(email)!!).child("last_name").setValue(lastname!!.text.toString())
                        openActivity(LoginActivity::class.java)
                        mProgress!!.dismiss()
                        finish()
                    }
                }
                Toast.makeText(this, "createUserWithEmail:success", Toast.LENGTH_LONG).show()
                handler.postDelayed(runnable!!, 2000)


            } else {

                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                mProgress!!.dismiss()
            }


        }
    }
    fun getSHA(input: String): String? {
        try {
            val md = MessageDigest.getInstance("SHA-256")
            val messageDigest = md.digest(input.toByteArray())
            val num = BigInteger(1, messageDigest)
            var hashText = num.toString(16)
            while (hashText.length < 32) {
                hashText = "0$hashText"
            }
            return hashText
        } catch (ex: NoSuchAlgorithmException) {
            println("Exception Occured: ${ex.message}")
            return null
        }
    }


    
}