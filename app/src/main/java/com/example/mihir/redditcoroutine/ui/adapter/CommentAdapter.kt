package com.example.mihir.redditcoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.data.local.entity.CommentEntity
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.ui.viewholder.CommentHeaderViewHolder
import com.example.mihir.redditcoroutine.ui.viewholder.CommentViewHolder
import com.example.mihir.redditcoroutine.ui.viewholder.MoreCommentViewHolder

class CommentAdapter : ListAdapterWithHeader<CommentEntity, RecyclerView.ViewHolder>(diffCallBack) {

    private var postEntity: PostEntity? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.recycler_item_post_title -> CommentHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_post_title, parent, false))
            R.layout.recycler_item_comment -> CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_comment, parent, false))
            R.layout.recycler_item_more_comment -> MoreCommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_more_comment, parent, false))
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentHeaderViewHolder -> holder.bindTo(postEntity)
            is CommentViewHolder -> holder.bindTo(getItem(position))
            is MoreCommentViewHolder -> holder.bindTo(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == HEADER_POSITION -> R.layout.recycler_item_post_title
            getItem(position).body != null -> R.layout.recycler_item_comment
            else -> R.layout.recycler_item_more_comment
        }
    }

    fun setHeaderItem(newPostEntity: PostEntity) {
        val previousItem = this.postEntity
        this.postEntity = newPostEntity
        if (previousItem != newPostEntity) {
            notifyItemChanged(HEADER_POSITION)
        }
    }

    companion object {
        const val HEADER_POSITION = 0
        private val diffCallBack = object : DiffUtil.ItemCallback<CommentEntity>() {
            override fun areItemsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}