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
import com.example.mihir.redditcoroutine.ui.adapter.SubredditListListAdapter
import com.example.mihir.redditcoroutine.ui.viewmodel.SubredditListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_subreddit_list.view.*
import kotlinx.android.synthetic.main.toolbar.*

class SubredditListFragment : BaseFragment() {

    private lateinit var viewModel: SubredditListViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    private val subredditListAdapter = SubredditListListAdapter().apply {
        onItemClick = { fragmentNavigation.pushFragment(SubredditFragment.newInstance(it.subredditName)) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subreddit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNavigation.setToolbar(toolbar)
        view.recyclerview_subreddits.apply {
            adapter = subredditListAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(context, 1))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = Injection.provideViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SubredditListViewModel::class.java)

        viewModel.tokenEntity.observe(viewLifecycleOwner, Observer { token ->
            viewModel.provide(token.refresh_token).observe(viewLifecycleOwner, Observer {
                subredditListAdapter.submitList(it)
            })
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(this.view!!, it, Snackbar.LENGTH_LONG).show()
        })
    }
}
