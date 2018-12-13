package com.example.mihir.redditcoroutine.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.mihir.redditcoroutine.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        FragNavController.RootFragmentListener,
        BaseFragment.FragmentNavigation,
        BaseFragment.ActivityNavigation,
        PostOptionsBottomSheet.ItemSelectedListener {
    override fun viewSubreddit(fragment: SubredditFragment) {
        fragNavController.clearDialogFragment()
        fragNavController.pushFragment(fragment)
    }

    override fun viewProfile(author: String) {
        
    }


    val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.container)
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.homeFragment -> fragNavController.switchTab(0)
            R.id.subredditListFragment -> fragNavController.switchTab(1)
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragNavController.apply {
            rootFragmentListener = this@MainActivity
            createEager = false
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
        }
        fragNavController.initialize(0, savedInstanceState)
        if (savedInstanceState == null)
            navigation.selectedItemId = 0
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemReselectedListener { fragNavController.clearStack() }
    }

    override fun pushFragment(fragment: Fragment) {
        fragNavController.pushFragment(fragment)
    }

    override fun pushDialogFragment(dialogFragment: DialogFragment) {
        fragNavController.showDialogFragment(dialogFragment)
    }

    override fun pushActivty(intent: Intent) {
        startActivity(intent)
    }

    override fun popFragment() {
        fragNavController.popFragment()
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState!!)
    }

    override val numberOfRootFragments: Int
        get() = 2

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment()
            1 -> SubredditListFragment()
            else -> throw IllegalStateException("Unkown Index")
        }
    }

    override fun onBackPressed() {
        if (fragNavController.isRootFragment)
            super.onBackPressed()
        else
            fragNavController.popFragment()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
