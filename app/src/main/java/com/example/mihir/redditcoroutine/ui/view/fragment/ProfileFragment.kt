package com.example.mihir.redditcoroutine.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mihir.redditcoroutine.Injection
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.ui.ViewModelFactory
import com.example.mihir.redditcoroutine.ui.view.activity.LoginActivity
import com.example.mihir.redditcoroutine.ui.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.button.setOnClickListener {
            activityNavigation.startActivityForResult(LoginActivity.newIntent(context!!))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = Injection.provideViewModelFactory(context!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)

        viewModel.tokenEntity.observe(viewLifecycleOwner, Observer {
            if (it.scope == "*")
                button.visibility = View.VISIBLE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null)
                Snackbar.make(this.view!!, it, Snackbar.LENGTH_LONG).show()
            viewModel.errorShown()
        })

        viewModel.liveTokenEntity.observe(viewLifecycleOwner, Observer {
            if (viewModel.tokenEntity.value != null)
                if (it.refresh_token == viewModel.tokenEntity.value!!.refresh_token) {
                    viewModel.updateToken()
                } else {

                }
        })
    }

}
