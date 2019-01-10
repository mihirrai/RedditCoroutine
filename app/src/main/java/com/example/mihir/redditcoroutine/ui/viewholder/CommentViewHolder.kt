package com.example.mihir.redditcoroutine.ui.viewholder

import android.net.Uri
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.StringUtil
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity
import me.saket.bettermovementmethod.BetterLinkMovementMethod

class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view), StringUtil {

    val details = view.findViewById<TextView>(R.id.details_comment)
    val body = view.findViewById<TextView>(R.id.body_comment)
    val divider = view.findViewById<View>(R.id.divider)

    fun bindTo(children: CommentEntity?) {
        if (children?.body != null) {
            details.text = getCommentDetails(children)
            setMarkdown(itemView.context, children.body, body)
            BetterLinkMovementMethod.linkify(Linkify.ALL, body)
                    .setOnLinkClickListener { textView, url ->
                        if (!url.startsWith("/")) {
                            val tabBuilder = CustomTabsIntent.Builder()
                            val tabIntent = tabBuilder.build()
                            tabIntent.launchUrl(itemView.context, Uri.parse(url))
                        }
                        true
                    }
            if (children.depth > 0) {
                divider.visibility = View.VISIBLE
                divider.setBackgroundColor(getDividerColor(children.depth)!!)
                itemView.setPadding(10 * children.depth, itemView.paddingTop, itemView.paddingRight, itemView.paddingBottom)
            }
        }
    }
}