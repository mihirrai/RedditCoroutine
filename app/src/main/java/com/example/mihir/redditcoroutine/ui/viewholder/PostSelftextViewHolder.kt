package com.example.mihir.redditcoroutine.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.StringUtil
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity

class PostSelftextViewHolder(view: View) : RecyclerView.ViewHolder(view), StringUtil {

    val title = view.findViewById<TextView>(R.id.title_post)
    val details = view.findViewById<TextView>(R.id.details_post)
    val selftext=view.findViewById<TextView>(R.id.selftext_post)
    val stats = view.findViewById<TextView>(R.id.stats_post)

    fun bindTo(item: PostEntity) {
        title.text = item.title
        details.text = getPostDetails(item, itemView.context)
        getMarkDown(item.selftext, itemView.context, selftext)
        stats.text = getPostStats(item)
    }
}