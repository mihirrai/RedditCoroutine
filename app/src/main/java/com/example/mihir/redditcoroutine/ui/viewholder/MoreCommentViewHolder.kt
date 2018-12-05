package com.example.mihir.redditcoroutine.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity

class MoreCommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val details = view.findViewById<TextView>(R.id.more_count_comment)

    fun bindTo(children: CommentEntity?) {
//        if (children.count != 0)
//            details.text = children.data.count.toString() + " more comments"
//        else
//            details.text="Continue Thread"
//        itemView.setPadding(10 * children.data.depth, 0, 0, 0)
    }

}