package com.example.mihir.redditcoroutine.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.remote.PostDetailResponse
import com.example.mihir.redditcoroutine.data.repository.PostDetailRepository
import com.example.mihir.redditcoroutine.data.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostViewModel(private val tokenRepository: TokenRepository, private val postDetailRepository: PostDetailRepository) : ViewModel(), CoroutineScope {
    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    val list: MutableList<PostDetailResponse.Data.Children> = arrayListOf()
    val res = MutableLiveData<List<PostDetailResponse>>()
    val comms = MutableLiveData<List<PostDetailResponse.Data.Children>>()

    val localPost = MutableLiveData<PostEntity>()

    fun test(subreddit: String, article: String) {
        launch {
            val t = postDetailRepository.getDetails(tokenRepository.getToken().access_token, subreddit, article)
            if (t.isSuccessful) {
                dump(t.body()!![1].data.children)
            }
            res.postValue(t.body()!!)
            comms.postValue(list)
            loading.postValue(false)
        }
    }

    fun updateLocalPost(id: String) {
        launch {
            postDetailRepository.updateLocalPosts(id,
                    res.value!![0].data.children[0].data.score,
                    res.value!![0].data.children[0].data.numComments)
            getLocalPost(id)
        }
    }

    fun getLocalPost(id: String) {
        launch {
            localPost.postValue(postDetailRepository.getLocalPost(id))
        }
    }


    fun dump(children: List<PostDetailResponse.Data.Children>) {
        children.forEach {
            list.add(it)
            if (it.data.replies != null)
                dump(it.data.replies.data.children)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
