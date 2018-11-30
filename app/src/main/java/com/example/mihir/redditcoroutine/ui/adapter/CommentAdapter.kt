package com.example.mihir.redditcoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.data.remote.PostDetailResponse
import com.example.mihir.redditcoroutine.ui.viewholder.CommentViewHolder
import com.example.mihir.redditcoroutine.ui.viewholder.MoreCommentViewHolder

class CommentAdapter : ListAdapter<PostDetailResponse.Data.Children, RecyclerView.ViewHolder>(diffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NORMAL -> CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_comment, parent, false))
            else -> MoreCommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_more_comment, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_NORMAL -> (holder as CommentViewHolder).bindTo(getItem(position))
            else -> (holder as MoreCommentViewHolder).bindTo(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position).data.body != null -> TYPE_NORMAL
            else -> TYPE_MORE
        }
    }

    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_MORE = 1
        private val diffCallBack = object : DiffUtil.ItemCallback<PostDetailResponse.Data.Children>() {
            override fun areItemsTheSame(oldItem: PostDetailResponse.Data.Children, newItem: PostDetailResponse.Data.Children): Boolean {
                return oldItem.data.id == newItem.data.id
            }

            override fun areContentsTheSame(oldItem: PostDetailResponse.Data.Children, newItem: PostDetailResponse.Data.Children): Boolean {
                return oldItem == newItem
            }
        }
    }

}