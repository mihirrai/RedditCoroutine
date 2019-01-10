package com.example.mihir.redditcoroutine.ui.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.ui.view.PostOptionsBottomSheet
import com.example.mihir.redditcoroutine.ui.view.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
        FragNavController.RootFragmentListener,
        BaseFragment.FragmentNavigation,
        BaseFragment.ActivityNavigation,
        PostOptionsBottomSheet.ItemSelectedListener {

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.container)
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.homeFragment -> fragNavController.switchTab(0)
            R.id.subredditListFragment -> fragNavController.switchTab(1)
            R.id.profileFragment -> fragNavController.switchTab(2)
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

    override fun startNewActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun startActivityForResult(newIntent: Intent) {
        startActivityForResult(newIntent, LOGIN_REQUEST)
    }

    override fun popFragment() {
        fragNavController.popFragment()
    }

    override fun viewSubreddit(fragment: SubredditFragment) {
        fragNavController.clearDialogFragment()
        fragNavController.pushFragment(fragment)
    }

    override fun viewProfile(author: String) {

    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                GlobalScope.launch(Dispatchers.IO) {
                    AppDatabase.getInstance(this@MainActivity).runInTransaction {
                        AppDatabase.getInstance(this@MainActivity).postDao().deleteAll()
                        AppDatabase.getInstance(this@MainActivity).subredditDao().deleteAll()
                        AppDatabase.getInstance(this@MainActivity).commentDao().deleteAll()
                        launch(Dispatchers.Main) { recreate() }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState!!)
    }

    override val numberOfRootFragments: Int
        get() = 3

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment()
            1 -> SubredditListFragment()
            2 -> ProfileFragment()
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

    companion object {
        const val LOGIN_REQUEST = 1
    }
}
