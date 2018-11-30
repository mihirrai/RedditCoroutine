package com.example.mihir.redditcoroutine

import android.content.Context
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.ui.ViewModelFactory

object Injection {

    fun provideLocalDataSource(context: Context): AppDatabase {
        val database = AppDatabase.getInstance(context)
        return database
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideLocalDataSource(context)
        return ViewModelFactory(dataSource)
    }
}