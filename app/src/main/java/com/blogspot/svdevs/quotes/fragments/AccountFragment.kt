package com.blogspot.svdevs.quotes.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.blogspot.svdevs.quotes.CreatePostActivity
import com.blogspot.svdevs.quotes.R
import com.blogspot.svdevs.quotes.profile.tabs.VPAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_account, container, false)

        val addButton = view.findViewById<ImageView>(R.id.addBtn)
        addButton.setOnClickListener { startActivity(Intent(activity,CreatePostActivity::class.java)) }


        val tableLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        val adapter = activity?.let { VPAdapter(it.supportFragmentManager,lifecycle) }

        viewPager.adapter = adapter

        TabLayoutMediator(tableLayout,viewPager){tab,pos->
            when(pos){
                0-> tab.text = "My Posts"
                1-> tab.text = "Liked Posts"
            }
        }.attach()

        return view
    }

}