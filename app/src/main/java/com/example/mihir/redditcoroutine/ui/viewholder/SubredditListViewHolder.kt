package com.example.mihir.redditcoroutine.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.GlideApp
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity

class SubredditListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val name = view.findViewById<TextView>(R.id.name_subreddit)
    val icon = view.findViewById<ImageView>(R.id.icon_subreddit)

    fun bindTo(item: SubredditEntity) {
        name.text = item.subredditName
        GlideApp.with(itemView)
                .load(item.iconUrl)
                .into(icon)
    }
}