package com.example.mihir.redditcoroutine.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mihir.redditcoroutine.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.menu_post_options.view.*

class PostOptionsBottomSheet : BottomSheetDialogFragment() {

    lateinit var itemSelectedListener: ItemSelectedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ItemSelectedListener)
            itemSelectedListener = context
        else
            d("context", context.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.menu_post_options, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.view_profile_post.setOnClickListener {
            itemSelectedListener.viewProfile(arguments?.getString("author").toString())

        }
        view.view_subreddit_post.setOnClickListener {
            itemSelectedListener.viewSubreddit(SubredditFragment.newInstance(arguments?.getString("subreddit").toString()))
            d("clicked", arguments?.getString("subreddit").toString())
        }
    }

    interface ItemSelectedListener {
        fun viewProfile(author: String)
        fun viewSubreddit(fragment: SubredditFragment)
    }

    companion object {
        fun newInstance(author: String, subredditName: String, postId: String): PostOptionsBottomSheet {
            val args = Bundle()
            args.putString("author", author)
            args.putString("subreddit", subredditName)
            args.putString("post_id", postId)
            val fragment = PostOptionsBottomSheet()
            fragment.arguments = args
            return fragment
        }
    }
}