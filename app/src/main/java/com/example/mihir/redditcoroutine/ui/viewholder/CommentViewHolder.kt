package com.example.mihir.redditcoroutine.ui.viewholder

import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.StringUtil
import com.example.mihir.redditcoroutine.data.remote.PostDetailResponse
import me.saket.bettermovementmethod.BetterLinkMovementMethod

class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view), StringUtil {

    val details = view.findViewById<TextView>(R.id.details_comment)
    val body = view.findViewById<TextView>(R.id.body_comment)

    fun bindTo(children: PostDetailResponse.Data.Children) {
        details.text = getCommentDetails(children)
        BetterLinkMovementMethod.linkify(Linkify.ALL, body)
        getMarkDown(children.data.body!!, itemView.context, body)
        itemView.setPadding(10 * children.data.depth, 0, 0, 0)
    }

}