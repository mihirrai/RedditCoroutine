package com.example.mihir.redditcoroutine.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mihir.redditcoroutine.data.local.entity.TokenEntity
import org.threeten.bp.OffsetDateTime

@Dao
interface TokenDao {
    @Insert
    fun insertToken(tokenEntity: TokenEntity)

    @Query("SELECT * FROM tokens WHERE active=1")
    suspend fun activeToken(): TokenEntity

    @Query("UPDATE tokens SET access_token=:accessToken, expiry=:expiry WHERE refresh_token=:refreshToken")
    fun updateToken(refreshToken: String, accessToken: String, expiry: OffsetDateTime)


    @Query("UPDATE tokens SET active=0 WHERE active=1")
    fun disableActiveToken()

    @Delete
    fun deleteToken(tokenEntity: TokenEntity)
}