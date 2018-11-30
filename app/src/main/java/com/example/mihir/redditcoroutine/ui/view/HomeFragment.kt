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
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeFragment : BaseFragment() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: ViewModelFactory


    val adapter = SubredditPostsListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNavigation.setToolbar(toolbar)

        view.recyclerview_posts_home.adapter = adapter
        view.recyclerview_posts_home.layoutManager = LinearLayoutManager(activity)
        view.recyclerview_posts_home.addItemDecoration(DividerItemDecoration(context, 1))
        view.recyclerview_posts_home.setItemViewCacheSize(20)

        adapter.onItemClick = {
            fragmentNavigation.pushFragment(PostFragment.newInstance(it.subreddit, it.id))
        }

        adapter.onMediaClick = {
            fragmentNavigation.pushFragment(MediaFragment.newInstance(it.url))
        }


        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
            view.recyclerview_posts_home.scrollToPosition(0)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = Injection.provideViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.livePagedListBuilder.observe(viewLifecycleOwner, Observer { t ->
            adapter.submitList(t)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            swipe_refresh.isRefreshing = it
        })
    }
}
