package com.example.mihir.redditcoroutine.data.repository

import android.util.Base64
import com.example.mihir.redditcoroutine.data.local.AppDatabase
import com.example.mihir.redditcoroutine.data.local.entity.TokenEntity
import com.example.mihir.redditcoroutine.data.remote.RedditAPI
import com.example.mihir.redditcoroutine.data.remote.response.TokenResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.threeten.bp.OffsetDateTime
import retrofit2.Response

class TokenRepository(val database: AppDatabase) {

    val api = RedditAPI.api

    suspend fun getToken(): TokenEntity {
        val token = getLocalAccessToken()
        when {
            token == null -> {
                val response = getRemoteAccessToken().await()
                val newToken = mapToEntity(response.body()!!)
                insertToken(newToken)
                return newToken
            }
            token.expiry.isBefore(OffsetDateTime.now()) -> {
                val response = refreshAccessToken(token.refresh_token).await()
                val newToken = response.body()!!
                with(newToken) {
                    updateToken(token.refresh_token, this.accessToken, OffsetDateTime.now().plusSeconds(this.expiresIn.toLong()))
                }
                return getLocalAccessToken()
            }
            else -> {
                return token
            }
        }
    }

    private fun mapToEntity(tokenResponse: TokenResponse): TokenEntity {
        return TokenEntity(tokenResponse.refreshToken, tokenResponse.scope, tokenResponse.accessToken, OffsetDateTime.now().plusSeconds(tokenResponse.expiresIn.toLong()), 1)
    }

    private fun getRemoteAccessToken(): Deferred<Response<TokenResponse>> {
        val headers = HashMap<String, String>()
        val fields = HashMap<String, String>()
        val auth = Base64.encodeToString("l3_rEXGA5nyt9A:".toByteArray(), Base64.NO_WRAP)
        headers["Authorization"] = "Basic $auth"
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        fields["grant_type"] = "https://oauth.reddit.com/grants/installed_client"
        fields["device_id"] = "DO_NOT_TRACK_THIS_DEVICE"
        fields["duration"] = "permanent"

        return api.getAccessToken(headers, fields)
    }

    private fun refreshAccessToken(refreshToken: String): Deferred<Response<TokenResponse>> {
        val headers = HashMap<String, String>()
        val fields = HashMap<String, String>()
        val auth = Base64.encodeToString("l3_rEXGA5nyt9A:".toByteArray(), Base64.NO_WRAP)
        headers["Authorization"] = "Basic $auth"
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        fields["grant_type"] = "refresh_token"
        fields["refresh_token"] = refreshToken

        return api.getAccessToken(headers, fields)
    }

    private suspend fun getLocalAccessToken(): TokenEntity {
        return database.tokenDao().activeToken()
    }

    private suspend fun insertToken(tokenEntity: TokenEntity) = coroutineScope {
        withContext(Dispatchers.IO) {
            database.tokenDao().insertToken(tokenEntity)
        }
    }

    private suspend fun updateToken(refresh_token: String, accessToken: String, now: OffsetDateTime) = coroutineScope {
        withContext(Dispatchers.IO) {
            database.tokenDao().updateToken(refresh_token, accessToken, now)
        }
    }
}