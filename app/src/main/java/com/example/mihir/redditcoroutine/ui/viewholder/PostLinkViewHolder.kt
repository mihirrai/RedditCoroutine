package com.example.mihir.redditcoroutine.ui.viewholder

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.GlideApp
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.StringUtil
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity

class PostLinkViewHolder(view: View) : RecyclerView.ViewHolder(view), StringUtil {

    val title = view.findViewById<TextView>(R.id.title_post)
    val details = view.findViewById<TextView>(R.id.details_post)
    val stats = view.findViewById<TextView>(R.id.stats_post)
    val thumbnail = view.findViewById<ImageView>(R.id.thumbnail_post)

    fun bindTo(item: PostEntity, onMediaClick: ((PostEntity) -> Unit)?) {
        title.text = item.title
        details.text = getPostDetails(item, itemView.context)
        stats.text = getPostStats(item)
        thumbnail.setOnClickListener { onMediaClick?.invoke(item) }
        GlideApp.with(itemView)
                .load(Html.fromHtml(item.thumbnailUrl).toString())
                .centerCrop()
                .into(thumbnail)
    }
}