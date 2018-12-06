package com.example.mihir.redditcoroutine.data.remote.response

import com.squareup.moshi.Json


data class MoreCommentResponse(
        @field:Json(name = "json") val json: TJson
) {
    data class TJson(
            @field:Json(name = "data")
            val `data`: Data,
            @field:Json(name = "errors")
            val errors: List<Any>
    ) {
        data class Data(
                @field:Json(name = "things")
                val things: List<Thing>
        ) {
            data class Thing(
                    @field:Json(name = "data")
                    val `data`: Data,
                    @field:Json(name = "kind")
                    val kind: String
            ) {
                data class Data(
                        @field:Json(name = "approved_at_utc")
                        val approvedAtUtc: Any?,
                        @field:Json(name = "approved_by")
                        val approvedBy: Any?,
                        @field:Json(name = "archived")
                        val archived: Boolean,
                        @field:Json(name = "author")
                        val author: String,
                        @field:Json(name = "author_flair_background_color")
                        val authorFlairBackgroundColor: Any?,
                        @field:Json(name = "author_flair_css_class")
                        val authorFlairCssClass: Any?,
                        @field:Json(name = "author_flair_richtext")
                        val authorFlairRichtext: List<Any>,
                        @field:Json(name = "author_flair_template_id")
                        val authorFlairTemplateId: Any?,
                        @field:Json(name = "author_flair_text")
                        val authorFlairText: Any?,
                        @field:Json(name = "author_flair_text_color")
                        val authorFlairTextColor: Any?,
                        @field:Json(name = "author_flair_type")
                        val authorFlairType: String,
                        @field:Json(name = "author_fullname")
                        val authorFullname: String,
                        @field:Json(name = "author_patreon_flair")
                        val authorPatreonFlair: Boolean,
                        @field:Json(name = "banned_at_utc")
                        val bannedAtUtc: Any?,
                        @field:Json(name = "banned_by")
                        val bannedBy: Any?,
                        @field:Json(name = "body")
                        val body: String?,
                        @field:Json(name = "body_html")
                        val bodyHtml: String,
                        @field:Json(name = "can_gild")
                        val canGild: Boolean,
                        @field:Json(name = "can_mod_post")
                        val canModPost: Boolean,
                        @field:Json(name = "collapsed")
                        val collapsed: Boolean,
                        @field:Json(name = "collapsed_reason")
                        val collapsedReason: Any?,
                        @field:Json(name = "controversiality")
                        val controversiality: Int,
                        @field:Json(name = "created")
                        val created: Int,
                        @field:Json(name = "created_utc")
                        val createdUtc: Int,
                        @field:Json(name = "depth")
                        val depth: Int,
                        @field:Json(name = "distinguished")
                        val distinguished: Any?,
                        @field:Json(name = "downs")
                        val downs: Int,
                        @field:Json(name = "edited")
                        val edited: Any,
                        @field:Json(name = "gilded")
                        val gilded: Int,
                        @field:Json(name = "gildings")
                        val gildings: Gildings,
                        @field:Json(name = "id")
                        val id: String,
                        @field:Json(name = "is_submitter")
                        val isSubmitter: Boolean,
                        @field:Json(name = "likes")
                        val likes: Any?,
                        @field:Json(name = "link_id")
                        val linkId: String,
                        @field:Json(name = "mod_note")
                        val modNote: Any?,
                        @field:Json(name = "mod_reason_by")
                        val modReasonBy: Any?,
                        @field:Json(name = "mod_reason_title")
                        val modReasonTitle: Any?,
                        @field:Json(name = "mod_reports")
                        val modReports: List<Any>,
                        @field:Json(name = "name")
                        val name: String,
                        @field:Json(name = "no_follow")
                        val noFollow: Boolean,
                        @field:Json(name = "num_reports")
                        val numReports: Any?,
                        @field:Json(name = "parent_id")
                        val parentId: String,
                        @field:Json(name = "permalink")
                        val permalink: String,
                        @field:Json(name = "removal_reason")
                        val removalReason: Any?,
                        @field:Json(name = "replies")
                        val replies: String,
                        @field:Json(name = "report_reasons")
                        val reportReasons: Any?,
                        @field:Json(name = "saved")
                        val saved: Boolean,
                        @field:Json(name = "score")
                        val score: Int,
                        @field:Json(name = "score_hidden")
                        val scoreHidden: Boolean,
                        @field:Json(name = "send_replies")
                        val sendReplies: Boolean,
                        @field:Json(name = "stickied")
                        val stickied: Boolean,
                        @field:Json(name = "subreddit")
                        val subreddit: String,
                        @field:Json(name = "subreddit_id")
                        val subredditId: String,
                        @field:Json(name = "subreddit_name_prefixed")
                        val subredditNamePrefixed: String,
                        @field:Json(name = "subreddit_type")
                        val subredditType: String,
                        @field:Json(name = "ups")
                        val ups: Int,
                        @field:Json(name = "user_reports")
                        val userReports: List<Any>
                ) {
                    data class Gildings(
                            @field:Json(name = "gid_1")
                            val gid1: Int,
                            @field:Json(name = "gid_2")
                            val gid2: Int,
                            @field:Json(name = "gid_3")
                            val gid3: Int
                    )
                }
            }
        }
    }
}