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

    private var postEntity: PostEntity? = null
    var onMoreItemClick: ((CommentEntity) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> CommentHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_comment_header, parent, false))
            TYPE_COMMENT -> CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_comment, parent, false))
            TYPE_MORE_COMMENT -> MoreCommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_more_comment, parent, false))
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentHeaderViewHolder -> holder.bindTo(postEntity)
            is CommentViewHolder -> holder.bindTo(getItem(position))
            is MoreCommentViewHolder -> {
                holder.bindTo(getItem(position))
                holder.itemView.setOnClickListener { onMoreItemClick!!.invoke(getItem(position)!!) }
            }
            else -> throw java.lang.IllegalArgumentException("Unknown holder $holder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == HEADER_POSITION -> TYPE_HEADER
            getItem(position)?.body != null -> TYPE_COMMENT
            else -> TYPE_MORE_COMMENT
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
        const val TYPE_HEADER = 0
        const val TYPE_COMMENT = 1
        const val TYPE_MORE_COMMENT = 2
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