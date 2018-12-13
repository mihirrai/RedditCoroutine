package com.example.mihir.redditcoroutine.data.remote

import android.util.Log
import com.example.mihir.redditcoroutine.data.remote.response.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface RedditAPI {
    @FormUrlEncoded
    @POST("api/v1/access_token/")
    fun getAccessToken(@HeaderMap headers: Map<String, String>,
                       @FieldMap fields: Map<String, String>): Deferred<Response<TokenResponse>>

    @FormUrlEncoded
    @POST("api/v1/authorize/")
    fun authorize(@HeaderMap headers: Map<String, String>,
                  @FieldMap fields: Map<String, String>): Deferred<Response<TokenResponse>>

    @GET("./?raw_json=1")
    fun getFrontpage(@HeaderMap headers: Map<String, String>,
                     @Query("after") after: String): Deferred<Response<SubredditResponse>>

    @GET("/r/{subreddit}/?raw_json=1")
    fun getSubreddit(@HeaderMap headers: Map<String, String>,
                     @Path("subreddit") subreddit: String,
                     @QueryMap options: Map<String, String>): Deferred<Response<SubredditResponse>>

    @GET("subreddits/?raw_json=1")
    fun getSubredditList(@HeaderMap headers: Map<String, String>,
                         @QueryMap options: Map<String, String>): Deferred<Response<SubredditListResponse>>

    @GET("subreddits/default/?raw_json=1")
    fun getDefaultSubreddits(@HeaderMap headers: Map<String, String>,
                             @QueryMap options: Map<String, String>): Deferred<Response<SubredditListResponse>>

    @GET("/r/{subreddit}/comments/{article}/?raw_json=1")
    fun getPostDetails(@HeaderMap headers: Map<String, String>,
                       @Path("subreddit") subreddit: String,
                       @Path("article") article: String): Deferred<Response<List<PostDetailResponse>>>

    @GET("/api/morechildren/?raw_json=1")
    fun getMoreComments(@HeaderMap headers: Map<String, String>,
                        @QueryMap options: Map<String, String>): Deferred<Response<MoreCommentResponse>>

    companion object {

        private const val BASE_URL = "https://www.reddit.com/"
        private const val OAUTH_URL = "https://oauth.reddit.com/"
        val api = create(HttpUrl.parse(BASE_URL)!!)
        val oauthApi = create(HttpUrl.parse(OAUTH_URL)!!)
        private fun create(httpUrl: HttpUrl): RedditAPI {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            val moshi = Moshi.Builder()
                    .add(MoshiAdapter())
                    .build()
            return Retrofit.Builder()
                    .baseUrl(httpUrl)
                    .client(client)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(RedditAPI::class.java)

        }
    }
}