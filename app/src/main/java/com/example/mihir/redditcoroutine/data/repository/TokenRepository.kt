package com.example.mihir.redditcoroutine.data.repository

import android.util.Base64
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.TokenEntity
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import com.example.mihir.redditcoroutine.data.remote.response.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import retrofit2.Response

class TokenRepository(val database: AppDatabase) {

    val api = RedditAPI.api

    suspend fun getToken(): TokenEntity {
        var token: TokenEntity? = null
        coroutineScope {
            async {
                val listLocal = getLocalAccessToken()
                when (listLocal.isEmpty()) {
                    true -> {
                        var remoteToken = getRemoteAccessToken()
                        if (remoteToken.isSuccessful) {
                            insertToken(TokenEntity(0, remoteToken.body()!!.scope, remoteToken.body()!!.accessToken, OffsetDateTime.now().plusSeconds(remoteToken.body()!!.expiresIn.toLong()), remoteToken.body()!!.refreshToken, 1))
                            token = TokenEntity(0, remoteToken.body()!!.scope, remoteToken.body()!!.accessToken, OffsetDateTime.now().plusSeconds(remoteToken.body()!!.expiresIn.toLong()), remoteToken.body()!!.refreshToken, 1)
                        }
                    }
                    false -> {
                        var localToken = listLocal[0]
                        if (localToken.expiry.isBefore(OffsetDateTime.now())) {
                            var refreshToken = refreshAccessToken(localToken.refresh_token)
                            if (refreshToken.isSuccessful) {
                                updateToken(localToken.refresh_token, refreshToken.body()!!.accessToken, OffsetDateTime.now().plusSeconds(refreshToken.body()!!.expiresIn.toLong()))
                                token = getLocalAccessToken()[0]
                            }
                        } else {
                            token = localToken
                        }
                    }
                }
            }.await()
        }

        return token!!
    }

    private suspend fun getRemoteAccessToken(): Response<TokenResponse> {
        val headers = HashMap<String, String>()
        val fields = HashMap<String, String>()
        val auth = Base64.encodeToString("l3_rEXGA5nyt9A:".toByteArray(), Base64.NO_WRAP)
        headers["Authorization"] = "Basic $auth"
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        fields["grant_type"] = "https://oauth.reddit.com/grants/installed_client"
        fields["device_id"] = "DO_NOT_TRACK_THIS_DEVICE"
        fields["duration"] = "permanent"

        return api.getAccessToken(headers, fields).await()
    }

    private suspend fun refreshAccessToken(refreshToken: String): Response<TokenResponse> {
        val headers = HashMap<String, String>()
        val fields = HashMap<String, String>()
        val auth = Base64.encodeToString("l3_rEXGA5nyt9A:".toByteArray(), Base64.NO_WRAP)
        headers["Authorization"] = "Basic $auth"
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        fields["grant_type"] = "refresh_token"
        fields["refresh_token"] = refreshToken

        return api.getAccessToken(headers, fields).await()
    }

    private suspend fun getLocalAccessToken(): List<TokenEntity> {
        return coroutineScope {
            async(Dispatchers.IO) {
                database.tokenDao().activeToken()
            }.await()
        }
    }

    suspend fun insertToken(tokenEntity: TokenEntity) = coroutineScope {
        launch(Dispatchers.IO) {
            database.tokenDao().insertToken(tokenEntity)
        }
    }

    suspend fun updateToken(refresh_token: String, accessToken: String, now: OffsetDateTime) = coroutineScope {
        async(Dispatchers.IO) {
            database.tokenDao().updateToken(refresh_token, accessToken, now)
        }.await()
    }
}