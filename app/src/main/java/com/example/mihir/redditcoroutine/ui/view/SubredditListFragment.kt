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
import com.example.mihir.redditcoroutine.ui.adapter.SubredditListListAdapter
import com.example.mihir.redditcoroutine.ui.viewmodel.SubredditListViewModel
import kotlinx.android.synthetic.main.subreddit_list_fragment.view.*
import kotlinx.android.synthetic.main.toolbar.*

class SubredditListFragment : BaseFragment() {


    val adapter = SubredditListListAdapter()

    private lateinit var viewModel: SubredditListViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.subreddit_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNavigation.setToolbar(toolbar)

        view.recyclerview_subreddits.adapter = adapter
        view.recyclerview_subreddits.layoutManager = LinearLayoutManager(activity)
        view.recyclerview_subreddits.addItemDecoration(DividerItemDecoration(context, 1))

        adapter.onItemClick = {
            fragmentNavigation.pushFragment(SubredditFragment.newInstance(it.subredditName))
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = Injection.provideViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SubredditListViewModel::class.java)

        viewModel.token.observe(viewLifecycleOwner, Observer { it ->
            if (it.isEmpty())
                viewModel.getToken()
            else
                viewModel.provide(it).observe(viewLifecycleOwner, Observer { list ->
                    adapter.submitList(list)
                })
        })


//        viewModel.recycler_item_post_link().observe(this, Observer {
//            d("size",it.size.toString())
//            adapter.submitList(it)
//        })
    }
}
