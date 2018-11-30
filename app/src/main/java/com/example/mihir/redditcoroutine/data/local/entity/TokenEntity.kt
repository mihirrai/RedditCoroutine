package com.example.mihir.redditcoroutine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "tokens")
data class TokenEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val scope: String,
        val access_token: String,
        val expiry: OffsetDateTime,
        val refresh_token: String,
        val active: Int
)