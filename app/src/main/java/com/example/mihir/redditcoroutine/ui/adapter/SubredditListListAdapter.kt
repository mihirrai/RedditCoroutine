package com.example.mihir.redditcoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.data.local.entity.SubredditEntity
import com.example.mihir.redditcoroutine.ui.viewholder.SubredditListViewHolder

class SubredditListListAdapter : PagedListAdapter<SubredditEntity, SubredditListViewHolder>(diffCallBack) {

    var onItemClick: ((SubredditEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_subreddit_list, parent, false)
        return SubredditListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubredditListViewHolder, position: Int) {
        if (getItem(position) != null) {
            holder.bindTo(getItem(position)!!)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(getItem(position)!!)
            }
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<SubredditEntity>() {
            override fun areItemsTheSame(oldItem: SubredditEntity, newItem: SubredditEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SubredditEntity, newItem: SubredditEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}