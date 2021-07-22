package com.blogspot.svdevs.quotes.other

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class MainViewModel(val context: Context): ViewModel() {
    private var quoteList : Array<Quote> = emptyArray<Quote>()
     var index = 0

    init {
        quoteList = loadFromJson()
    }

    private fun loadFromJson(): Array<Quote> {
        val ipStream = context.assets.open("quotes.json")
        val size:Int = ipStream.available()
        val buffer = ByteArray(size)
        ipStream.read(buffer)
        ipStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json,Array<Quote>::class.java)
    }

    fun getQuote() = quoteList[index]

    fun nextQuote() = quoteList[++index]

    fun previousQuote() = quoteList[--index]
}