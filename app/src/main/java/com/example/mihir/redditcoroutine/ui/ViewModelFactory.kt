package com.example.mihir.redditcoroutine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.repository.PostDetailRepository
import com.example.mihir.redditcoroutine.data.repository.PostRepository
import com.example.mihir.redditcoroutine.data.repository.SubredditRepository
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import com.example.mihir.redditcoroutine.ui.viewmodel.HomeViewModel
import com.example.mihir.redditcoroutine.ui.viewmodel.PostViewModel
import com.example.mihir.redditcoroutine.ui.viewmodel.SubredditListViewModel
import com.example.mihir.redditcoroutine.ui.viewmodel.SubredditViewModel

class ViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(TokenRepository(database), PostRepository(database)) as T
            modelClass.isAssignableFrom(SubredditListViewModel::class.java) -> SubredditListViewModel(TokenRepository(database), SubredditRepository(database)) as T
            modelClass.isAssignableFrom(SubredditViewModel::class.java) -> SubredditViewModel(TokenRepository(database), PostRepository(database)) as T
            modelClass.isAssignableFrom(PostViewModel::class.java) -> PostViewModel(TokenRepository(database),PostDetailRepository(database)) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}