package com.example.mihir.redditcoroutine.data.remote

import com.example.mihir.redditcoroutine.data.remote.response.PostDetailResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson

class MoshiAdapter{
    @ToJson
    fun toJson(@AdapterCheck value: PostDetailResponse?): PostDetailResponse? {
        return value
    }

    @FromJson
    @AdapterCheck
    fun fromJson(reader: JsonReader, delegate: JsonAdapter<PostDetailResponse>)= when (reader.peek()) {
        JsonReader.Token.STRING -> when (reader.nextString()) {
            "" -> null // Response was false
            else ->
                throw IllegalStateException("Non-false boolean for @JsonObjectOrFalse field")
        }
        JsonReader.Token.BEGIN_OBJECT -> delegate.fromJson(reader)
        else ->
            throw IllegalStateException("Non-object-non-boolean value for @JsonObjectOrFalse field")
    }
}