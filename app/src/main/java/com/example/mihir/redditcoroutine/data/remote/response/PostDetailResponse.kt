package com.example.mihir.redditcoroutine.data.remote.response

import com.example.mihir.redditcoroutine.data.remote.AdapterCheck
import com.squareup.moshi.Json

data class PostDetailResponse(
        @field:Json(name = "data")
        val data: Data,
        @field:Json(name = "kind")
        val kind: String
) {
    data class Data(
            @field:Json(name = "after")
            val after: Any?,
            @field:Json(name = "before")
            val before: Any?,
            @field:Json(name = "children")
            val children: List<Children>,
            @field:Json(name = "dist")
            val dist: Any?,
            @field:Json(name = "modhash")
            val modhash: String
    ) {
        data class Children(
                @field:Json(name = "data")
                val data: Data,
                @field:Json(name = "kind")
                val kind: String
        ) {
            data class Data(
                    @field:Json(name = "author")
                    val author:String,
                    @field:Json(name = "body")
                    val body: String,
                    @field:Json(name = "body_html")
                    val bodyHtml:String?,
                    @field:Json(name = "children")
                    val children: List<String>?,
                    @field:Json(name = "count")
                    val count: Int,
                    @field:Json(name = "created_utc")
                    val createdUtc:Int,
                    @field:Json(name = "depth")
                    val depth: Int,
                    @field:Json(name = "id")
                    val id: String,
                    @field:Json(name = "is_submitter")
                    val isSubmitter: Boolean,
                    @field:Json(name = "name")
                    val name: String,
                    @field:Json(name="num_comments")
                    val numComments:Int,
                    @field:Json(name = "parent_id")
                    val parentId: String,
                    @field:Json(name = "replies") @field:AdapterCheck
                    val replies: PostDetailResponse?,
                    @field:Json(name="score")
                    val score:Int,
                    @field:Json(name="title")
                    val title:String
            )
        }
    }

}