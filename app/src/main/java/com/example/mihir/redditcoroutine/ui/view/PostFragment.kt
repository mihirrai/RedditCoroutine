package com.example.mihir.redditcoroutine.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mihir.redditcoroutine.Injection
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.ui.ViewModelFactory
import com.example.mihir.redditcoroutine.ui.adapter.CommentAdapter
import com.example.mihir.redditcoroutine.ui.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.post_fragment.*
import kotlinx.android.synthetic.main.post_fragment.view.*
import kotlinx.android.synthetic.main.toolbar.*

class PostFragment : BaseFragment() {

    companion object {
        fun newInstance(subredditName: String, postId: String): PostFragment {
            val args = Bundle()
            args.putString("subreddit", subredditName)
            val id = postId.substringAfter("_")
            args.putString("id", id)
            val fragment = PostFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: PostViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var subreddit: String
    private lateinit var id: String
    val adapter = CommentAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        subreddit = arguments?.getString("subreddit").toString()
        id = arguments?.getString("id").toString()
        if (subreddit.isEmpty() || id.isEmpty())
            throw IllegalStateException()
        return inflater.inflate(R.layout.post_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = subreddit
        fragmentNavigation.setToolbar(toolbar)
        view.recyclerview_comments.adapter = adapter
        view.recyclerview_comments.layoutManager = LinearLayoutManager(activity)
        view.recyclerview_comments.addItemDecoration(DividerItemDecoration(context, 1))

        adapter.onMoreItemClick = {
            viewModel.loadMoreComments(id, it)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = Injection.provideViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostViewModel::class.java)

        viewModel.localPost(id).observe(viewLifecycleOwner, Observer {
            adapter.setHeaderItem(it)
        })

        viewModel.list(subreddit, id).observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            swipe_refresh.isRefreshing = it
        })
    }
}
