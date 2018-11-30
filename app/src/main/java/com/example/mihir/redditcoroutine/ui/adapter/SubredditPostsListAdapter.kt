package com.example.mihir.redditcoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.ui.viewholder.PostLinkViewHolder
import com.example.mihir.redditcoroutine.ui.viewholder.PostSelftextViewHolder
import com.example.mihir.redditcoroutine.ui.viewholder.SubredditPostViewHolder

class SubredditPostsListAdapter : PagedListAdapter<PostEntity, RecyclerView.ViewHolder>(diffCallBack) {

    var onItemClick: ((PostEntity) -> Unit)? = null
    var onMediaClick: ((PostEntity) -> Unit)? = null
    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position)!!.isSelf && getItem(position)!!.selftext != "" -> TYPE_SELFTEXT
            getItem(position)!!.thumbnailUrl != null -> TYPE_LINK
            else -> TYPE_TITLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LINK -> PostLinkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_post_link, parent, false))
            TYPE_SELFTEXT -> PostSelftextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_post_selftext, parent, false))
            else -> SubredditPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_post_title, parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position) != null) {
            when (holder.itemViewType) {
                TYPE_LINK -> (holder as PostLinkViewHolder).bindTo(this.getItem(position)!!, onMediaClick)
                TYPE_SELFTEXT -> (holder as PostSelftextViewHolder).bindTo(this.getItem(position)!!)
                else -> (holder as SubredditPostViewHolder).bindTo(this.getItem(position)!!)
            }
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(getItem(position)!!)
            }
        }

    }

    companion object {
        const val TYPE_LINK = 0
        const val TYPE_SELFTEXT = 1
        const val TYPE_TITLE = 2
        private val diffCallBack = object : DiffUtil.ItemCallback<PostEntity>() {
            override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}