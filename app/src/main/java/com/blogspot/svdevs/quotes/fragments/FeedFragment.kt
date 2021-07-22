package com.blogspot.svdevs.quotes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.svdevs.quotes.R
import com.blogspot.svdevs.quotes.adapter.PostsAdapter
import com.blogspot.svdevs.quotes.modal.Quotes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.lang.StringBuilder

class FeedFragment : Fragment() {

    private lateinit var firestoreDB: FirebaseFirestore
    private lateinit var quotes:MutableList<Quotes>
    private lateinit var adapter:PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestoreDB = FirebaseFirestore.getInstance()
        quotes = mutableListOf()

        adapter = PostsAdapter(requireActivity(),quotes)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_feed, container, false)

        val rvPosts = view.findViewById<RecyclerView>(R.id.rvPosts)
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(activity)

//        val tv = view.findViewById<TextView>(R.id.tvTemp)

        val postRef = firestoreDB
            .collection("posts")
            .limit(20)
            .orderBy("creation_time",Query.Direction.DESCENDING)
        postRef.addSnapshotListener { snapshot, exception ->

            if (exception != null || snapshot == null) {
                //error occured
                Toast.makeText(activity, "Something went wrong...", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val sb = StringBuilder()
            val quotesList = snapshot.toObjects(Quotes::class.java)
            quotes.clear()
            quotes.addAll(quotesList)
            Log.d("FEED", "onCreateView: $quotes")
            adapter.notifyDataSetChanged()
//            for (quotes in quotesList) {
//                sb.append("Post $quotes")
//            }
//            tv.text = sb.toString()


        }

        return view
    }
}