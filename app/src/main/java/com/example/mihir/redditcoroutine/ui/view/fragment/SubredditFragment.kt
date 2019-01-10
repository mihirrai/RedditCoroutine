package com.example.mihir.redditcoroutine.ui.view.fragment

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
import com.example.mihir.redditcoroutine.ui.view.PostOptionsBottomSheet
import com.example.mihir.redditcoroutine.ui.view.activity.ImageActivity
import com.example.mihir.redditcoroutine.ui.viewmodel.SubredditViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_subreddit.*
import kotlinx.android.synthetic.main.fragment_subreddit.view.*
import kotlinx.android.synthetic.main.toolbar.*


class SubredditFragment : BaseFragment() {

    private lateinit var viewModel: SubredditViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var subreddit: String
    private val postsListAdapter = SubredditPostsListAdapter().apply {
        onItemClick = { fragmentNavigation.pushFragment(PostFragment.newInstance(it.subreddit, it.id)) }
        onMediaClick = { activityNavigation.startActivity(ImageActivity.newIntent(context!!, it.url)) }
        onOptionsClick = { fragmentNavigation.pushDialogFragment(PostOptionsBottomSheet.newInstance(it.author, it.subreddit, it.id)) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        subreddit = arguments?.getString("subreddit").toString()
        if (subreddit.isEmpty())
            throw IllegalStateException()
        return inflater.inflate(R.layout.fragment_subreddit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = subreddit
        fragmentNavigation.setToolbar(toolbar)
        view.recyclerview_posts_subreddit.apply {
            adapter = postsListAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(context, 1))
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
            postsListAdapter.submitList(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            swipe_refresh.isRefreshing = it
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(this.view!!, it, Snackbar.LENGTH_LONG).show()
        })
    }

    companion object {
        fun newInstance(subredditName: String): SubredditFragment {
            val args = Bundle()
            args.putString("subreddit", subredditName)
            val fragment = SubredditFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
