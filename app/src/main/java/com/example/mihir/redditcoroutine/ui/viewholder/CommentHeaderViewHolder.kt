package com.example.mihir.redditcoroutine.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.GlideApp
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.StringUtil
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity

class CommentHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view), StringUtil {

    val title = view.findViewById<TextView>(R.id.title_post)
    val details = view.findViewById<TextView>(R.id.details_post)
    val selftext = view.findViewById<TextView>(R.id.selftext_post)
    val stats = view.findViewById<TextView>(R.id.stats_post)
    val thumbnail = view.findViewById<ImageView>(R.id.thumbnail_post)

    fun bindTo(postEntity: PostEntity?) {
        if (postEntity != null) {
            when {
                postEntity.isSelf && postEntity.selftext != null -> {
                    thumbnail.visibility = View.GONE
                    title.text = postEntity.title
                    details.text = getPostDetails(postEntity, itemView.context)
                    setMarkdown(itemView.context, postEntity.selftext, selftext)
                    stats.text = getPostStats(postEntity)
                }
                postEntity.thumbnailUrl != null -> {
                    selftext.visibility = View.GONE
                    title.text = postEntity.title
                    details.text = getPostDetails(postEntity, itemView.context)
                    stats.text = getPostStats(postEntity)
//                    thumbnail.setOnClickListener { onMediaClick?.invoke(item) }
                    GlideApp.with(itemView)
                            .load(postEntity.thumbnailUrl)
                            .centerCrop()
                            .into(thumbnail)
                }
                else -> {
                    thumbnail.visibility = View.GONE
                    selftext.visibility = View.GONE
                    title.text = postEntity.title
                    details.text = getPostDetails(postEntity, itemView.context)
                    stats.text = getPostStats(postEntity)
                }
            }

        }
    }

}