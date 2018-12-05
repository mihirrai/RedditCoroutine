package com.example.mihir.redditcoroutine.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.StringUtil
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity

class CommentHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view),StringUtil {

    val title = view.findViewById<TextView>(R.id.title_post)
    val stats = view.findViewById<TextView>(R.id.stats_post)

    fun bindTo(postEntity: PostEntity?) {
        if (postEntity != null) {
            title.text = postEntity.title
            stats.text = getPostStats(postEntity)
        }
    }

}