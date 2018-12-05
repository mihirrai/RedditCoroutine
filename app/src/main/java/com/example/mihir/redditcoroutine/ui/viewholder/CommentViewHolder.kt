package com.example.mihir.redditcoroutine.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.StringUtil
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity

class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view), StringUtil {

    val details = view.findViewById<TextView>(R.id.details_comment)
    val body = view.findViewById<TextView>(R.id.body_comment)

    fun bindTo(children: CommentEntity?) {
        if (children!=null) {
            details.text = getCommentDetails(children)
            body.text = children.body
        }
        //BetterLinkMovementMethod.linkify(Linkify.ALL, body)
        //getMarkDown(children.body!!, itemView.context, body)
        //itemView.setPadding(10 * children.depth, 0, 0, 0)
    }

}