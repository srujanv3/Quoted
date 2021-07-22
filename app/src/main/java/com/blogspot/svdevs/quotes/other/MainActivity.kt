package com.blogspot.svdevs.quotes.other

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.blogspot.svdevs.quotes.R

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private val quoteText:TextView
        get() = findViewById(R.id.quoteText)

    private val quoteAuthor:TextView
        get() = findViewById(R.id.quoteAuthor)

    private val previousBtn:ImageView
        get() = findViewById(R.id.btn_previous)

    private val nextBtn:ImageView
        get() = findViewById(R.id.btn_next)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sending the application context in MainViewModelFactory because application context survives the configuration changes in the application unlike the activity context(this)
        mainViewModel = ViewModelProvider(this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)
        setQuote(mainViewModel.getQuote())

    }

    fun setQuote(quote: Quote){
        quoteText.text = quote.text
        quoteAuthor.text = quote.author
    }

    fun previousQuote(view: View) {
       setQuote(mainViewModel.previousQuote())
    }
    fun nextQuote(view: View) {setQuote(mainViewModel.nextQuote())}

    fun shareQuote(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,mainViewModel.getQuote().text +"\n - "+ mainViewModel.getQuote().author)
        startActivity(intent)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}