package com.blogspot.svdevs.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.blogspot.svdevs.quotes.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var home_binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        home_binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(home_binding.tabs)


        val controller: NavController = Navigation.findNavController(this, R.id.main_nav_host)
        NavigationUI.setupWithNavController(home_binding.bottomNavigationView, controller)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}