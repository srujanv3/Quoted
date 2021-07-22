package com.blogspot.svdevs.quotes.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.blogspot.svdevs.quotes.HomeActivity
import com.blogspot.svdevs.quotes.R
import com.blogspot.svdevs.quotes.databinding.ActivitySignupBinding
import com.blogspot.svdevs.quotes.modal.User
import com.blogspot.svdevs.quotes.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var dbRef: DatabaseReference


    private val email:EditText
        get() = findViewById(R.id.et_email)

    private val password:EditText
        get() = findViewById(R.id.et_password)

    private val confirmPass:EditText
        get() = findViewById(R.id.et_confirmPass)

    private val username:EditText
        get() = findViewById(R.id.et_username)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference


        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.tvToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoginState()
    }

    private fun registerUser() {
        val remail:String = email.text.toString().trim()
        val rpassword:String = password.text.toString().trim()
        val rconfirmPass:String = confirmPass.text.toString().trim()

        val rusername : String = username.text.toString().trim()


        if(remail.isEmpty() && rpassword.isEmpty() && rconfirmPass.isEmpty()){
            showToast("Fields cannot be empty")
            return
        }

        if(rusername.isEmpty()){
            showToast("Enter a valid username")
            return
        }

        if(rpassword != rconfirmPass){
           showToast("Password and confirm password does not match")
            return
        }
        val user = User(rusername)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(remail, rpassword).await()
                withContext(Dispatchers.Main){
                    dbRef.createUser(firebaseAuth.uid!!,user){checkLoginState() }
//                    storeUserData(rusername)
                }

            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignupActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    // added fun
    private fun DatabaseReference.createUser(uid: String, user: User, onSuccess: () -> Unit) {
        val reference = child("Users").child(uid)
        reference.setValue(user)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    showToast("Failed to create user profile")
                }
            }
    }

    private fun storeUserData(rusername: String) {
        val user = User(rusername)
        dbRef.child(rusername).setValue(user)

    }

    private fun checkLoginState() {
        if(firebaseAuth.currentUser == null){
            return
        }else{
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}