package com.blogspot.svdevs.quotes

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.blogspot.svdevs.quotes.databinding.ActivityCreatePostBinding
import com.blogspot.svdevs.quotes.helper.FirebaseHelper
import com.blogspot.svdevs.quotes.helper.ValueEventListenerAdapter
import com.blogspot.svdevs.quotes.modal.User
import com.blogspot.svdevs.quotes.other.Quote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreatePostActivity : AppCompatActivity() {

    private lateinit var mFirebaseHelper: FirebaseHelper

    private lateinit var databaseRef: DatabaseReference
    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mUser:User

    private val quoteTxt: EditText
        get() = findViewById(R.id.quoteText)

    private val quoteAuth: EditText
        get() = findViewById(R.id.quoteAuthor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)

        auth = FirebaseAuth.getInstance()

        databaseRef = FirebaseDatabase.getInstance().getReference("Posts")

//        mFirebaseHelper.currentUserReference().addValueEventListener(ValueEventListenerAdapter{
//            mUser = it.getValue(User::class.java)!!
//        })


        binding.btnCreate.setOnClickListener {
            sendPostData()
        }

    }

    private fun sendPostData() {
        val quoteText: String = quoteTxt.text.toString()
        val quoteAuthor: String = quoteAuth.text.toString()
        val currUser: String =" mUser.username"

        if (quoteText.isEmpty()) {
            showToast("Fields cannot be empty...")
            return
        }
        if (quoteAuthor.isEmpty()) {
            showToast("Please provide an Author")
            return
        }

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Create Post ?")
        dialog.setPositiveButton("Yes") { dialogInterface, which ->
            //storePostData(quoteText, quoteAuthor, currUser)
            showToast(currUser)
        }
        dialog.setNegativeButton("No") { dialogInterface, which ->
            return@setNegativeButton
        }

        dialog.setCancelable(false)
        dialog.show()

    }

    private fun storePostData(quoteText: String, quoteAuthor: String, currUser: String) {
        val quote = Quote(quoteText, quoteAuthor)
        // showToast(quoteText +" "+ quoteAuthor)
        // databaseRef.child().setValue(quote)
    }
}