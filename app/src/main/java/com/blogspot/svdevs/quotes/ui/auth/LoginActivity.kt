package com.blogspot.svdevs.quotes.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.blogspot.svdevs.quotes.HomeActivity
import com.blogspot.svdevs.quotes.R
import com.blogspot.svdevs.quotes.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.tvToSignup.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

        }

    }

    override fun onStart() {
        super.onStart()
        checkLoginState()
    }


    fun loginUser() {
        val email: String = binding.etEmail.text.toString().trim()
        val password: String = binding.etPassword.text.toString().trim()

        if(email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    firebaseAuth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main){
                        checkLoginState()
                    }

                }catch (e:Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun checkLoginState() {
        if(firebaseAuth.currentUser == null){
            return
        }else{
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}

//    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
//        if (it.isSuccessful){
//
//        }else{
//            Toast.makeText(this,"Some error occured",Toast.LENGTH_SHORT).show()
//        }
//    }
//}else{
//    Toast.makeText(this,"Both fields needed",Toast.LENGTH_SHORT).show()
//}