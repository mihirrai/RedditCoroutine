package com.example.mihir.redditcoroutine.ui.view

import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    lateinit var fragmentNavigation: FragmentNavigation

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)
        fun setToolbar(toolbar: Toolbar)
    }
}