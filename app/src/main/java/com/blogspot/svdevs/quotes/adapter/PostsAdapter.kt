package com.blogspot.svdevs.quotes.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.svdevs.quotes.R
import com.blogspot.svdevs.quotes.modal.Quotes

class PostsAdapter(val context: Context,val posts:List<Quotes>):
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
      return posts.size
    }


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val tvUser = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvPostTime = itemView.findViewById<TextView>(R.id.tvCurrentTime)
        val tvQuoteText = itemView.findViewById<TextView>(R.id.quoteText)
        val tvQuoteAuthor = itemView.findViewById<TextView>(R.id.quoteAuthor)

        fun bind(quotes: Quotes) {
            tvUser.text = quotes.user?.username.toString()
            tvPostTime.text = DateUtils.getRelativeTimeSpanString(quotes.creationTime).toString()
            tvQuoteText.text = quotes.quote
            tvQuoteAuthor.text = quotes.author
        }

    }

}