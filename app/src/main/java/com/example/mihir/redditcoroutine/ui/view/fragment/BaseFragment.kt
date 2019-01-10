package com.example.mihir.redditcoroutine.ui.view.fragment

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    lateinit var fragmentNavigation: FragmentNavigation
    lateinit var activityNavigation: ActivityNavigation

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
        if (context is ActivityNavigation) {
            activityNavigation = context
        }
    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)
        fun pushDialogFragment(dialogFragment: DialogFragment)
        fun popFragment()
        fun setToolbar(toolbar: Toolbar)
    }

    interface ActivityNavigation {
        fun startNewActivity(intent: Intent)
        fun startActivityForResult(newIntent: Intent)
    }
}