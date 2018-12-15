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
import com.example.mihir.redditcoroutine.ui.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private val postsListAdapter = SubredditPostsListAdapter().apply {
        onItemClick = { fragmentNavigation.pushFragment(PostFragment.newInstance(it.subreddit, it.id)) }
        onMediaClick = { activityNavigation.pushActivty(ImageActivity.newIntent(context!!, it.url)) }
        onOptionsClick = { fragmentNavigation.pushDialogFragment(PostOptionsBottomSheet.newInstance(it.author, it.subreddit, it.id)) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNavigation.setToolbar(toolbar)
        view.recyclerview_posts_home.apply {
            adapter = postsListAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(context, 1))
        }
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = Injection.provideViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModel.livePagedListBuilder.observe(viewLifecycleOwner, Observer { t ->
            postsListAdapter.submitList(t)
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            swipe_refresh.isRefreshing = it
            recyclerview_posts_home.layoutManager!!.scrollToPosition(0)
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(this.view!!, it, Snackbar.LENGTH_LONG).show()
        })
    }
}
