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
import com.example.mihir.redditcoroutine.ui.adapter.SubredditPostsListAdapter
import com.example.mihir.redditcoroutine.ui.viewmodel.SubredditViewModel
import kotlinx.android.synthetic.main.subreddit_fragment.*
import kotlinx.android.synthetic.main.subreddit_fragment.view.*
import kotlinx.android.synthetic.main.toolbar.*


class SubredditFragment : BaseFragment() {

    companion object {
        fun newInstance(subredditName: String): SubredditFragment {
            val args = Bundle()
            args.putString("subreddit", subredditName)
            val fragment = SubredditFragment()
            fragment.arguments = args
            return fragment
        }
    }

    val adapter = SubredditPostsListAdapter()

    private lateinit var viewModel: SubredditViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var subreddit: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        subreddit = arguments?.getString("subreddit").toString()
        if (subreddit.isEmpty())
            throw IllegalStateException()
        return inflater.inflate(R.layout.subreddit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = subreddit
        fragmentNavigation.setToolbar(toolbar)
        view.recyclerview_posts_subreddit.adapter = adapter
        view.recyclerview_posts_subreddit.layoutManager = LinearLayoutManager(activity)
        view.recyclerview_posts_subreddit.addItemDecoration(DividerItemDecoration(context, 1))
        adapter.onItemClick = {
            fragmentNavigation.pushFragment(PostFragment.newInstance(it.subreddit, it.id))
        }
        adapter.onMediaClick = {
            activityNavigation.pushActivty(ImageActivity.newIntent(context!!, it.url))
        }
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh(subreddit)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = Injection.provideViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SubredditViewModel::class.java)
        viewModel.provide(subreddit).observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            swipe_refresh.isRefreshing = it
        })
    }
}
