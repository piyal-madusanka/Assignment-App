package com.max119.splashapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import java.util.regex.Pattern

open class BaceActivity: AppCompatActivity() {

fun mailValidation(myEmailStr:String,editText:EditText ): Boolean {
    if (myEmailStr.validEmail() && editText.nonEmpty()){
        return true
    }else{
        editText.error="Please Enter Valid Email"
        return false
    }
}
fun passwordValidator(password:String,editText: EditText):Boolean {
    var case = true
    if (!password.validator().nonEmpty().check()) {
        editText.error="Password field cannot be Empty"
       // Toast.makeText(this, "Password field cannot be Empty", Toast.LENGTH_SHORT).show()
        case = false
    }else if(password.length<8){
        editText.error="Password should be minimum 8 characters long"
     //   Toast.makeText(this,"Password should be minimum 8 characters long",Toast.LENGTH_LONG).show()
        case = false
    }
   else if (!password.validator().atleastOneNumber().check()) {
        editText.error="Password need at least one number"
        //Toast.makeText(this, "Password need at least one number", Toast.LENGTH_SHORT).show()
        case = false
    }
    else if (!password.validator().atleastOneUpperCase().check()) {
        editText.error="Password need atleast one uppercase letter "
       // Toast.makeText(this, "Password need atleast one uppercase letter ", Toast.LENGTH_SHORT).show()
        case=false
    }
    return case
}
  fun comparePaswords(ps1:String,ps2:String,ps3:EditText):Boolean{
      var x=ps1.equals(ps2)
      return if(!x){
          ps3.error="password didn't match for previous password"
          false
      }else{
          true
      }
  }
    fun validatenames(first:EditText,last:EditText):Boolean{
        var str:Boolean=true
        if(!first.nonEmpty()){
            first.error="First name cannot be Empty"
            str=false
        }else if(first.text.toString().length<3){
            first.error="First name need contain at least 3 characters"
            str=false
        }
       else if(!last.nonEmpty()){
            last.error="last name cannot be Empty"
            str=false
        } else if(last.text.toString().length<3){
        first.error="last name need contain at least 3 characters"
        str=false
       }

        return str
    }
    //Extension
    fun String.isNamevalid(): Boolean {
        val expression = "^[A-Za-z](.*)"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }
    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(this, it)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)

    }








}