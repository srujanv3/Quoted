package com.blogspot.svdevs.quotes.helper

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ValueEventListenerAdapter(val handler: (DataSnapshot) -> Unit): ValueEventListener {
    override fun onCancelled(error: DatabaseError) {
    }

    override fun onDataChange(data: DataSnapshot) {
        handler(data)
    }
}