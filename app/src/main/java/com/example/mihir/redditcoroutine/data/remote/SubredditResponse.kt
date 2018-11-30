package com.example.mihir.redditcoroutine.data.remote

import com.squareup.moshi.Json


data class SubredditResponse(
        @field:Json(name = "kind") val kind: String,
        @field:Json(name = "data") val data: Data
) {

    data class Data(
            @field:Json(name = "modhash") val modhash: String,
            @field:Json(name = "dist") val dist: Int,
            @field:Json(name = "children") val children: List<Children>,
            @field:Json(name = "after") val after: String,
            @field:Json(name = "before") val before: Any?
    ) {

        data class Children(
                @field:Json(name = "kind") val kind: String,
                @field:Json(name = "data") val data: Data
        ) {

            data class Data(
                    @field:Json(name = "approved_at_utc") val approvedAtUtc: Any?,
                    @field:Json(name = "subreddit") val subreddit: String,
                    @field:Json(name = "selftext") val selftext: String,
                    @field:Json(name = "author_fullname") val authorFullname: String,
                    @field:Json(name = "saved") val saved: Boolean,
                    @field:Json(name = "mod_reason_title") val modReasonTitle: Any?,
                    @field:Json(name = "gilded") val gilded: Int,
                    @field:Json(name = "clicked") val clicked: Boolean,
                    @field:Json(name = "title") val title: String,
                    @field:Json(name = "link_flair_richtext") val linkFlairRichtext: List<Any>,
                    @field:Json(name = "subreddit_name_prefixed") val subredditNamePrefixed: String,
                    @field:Json(name = "hidden") val hidden: Boolean,
                    @field:Json(name = "link_flair_css_class") val linkFlairCssClass: Any?,
                    @field:Json(name = "downs") val downs: Int,
                    @field:Json(name = "thumbnail_height") val thumbnailHeight: Int?,
                    @field:Json(name = "hide_score") val hideScore: Boolean,
                    @field:Json(name = "name") val name: String,
                    @field:Json(name = "quarantine") val quarantine: Boolean,
                    @field:Json(name = "link_flair_text_color") val linkFlairTextColor: String,
                    @field:Json(name = "author_flair_background_color") val authorFlairBackgroundColor: Any?,
                    @field:Json(name = "subreddit_type") val subredditType: String,
                    @field:Json(name = "ups") val ups: Int,
                    @field:Json(name = "domain") val domain: String,
                    @field:Json(name = "thumbnail_width") val thumbnailWidth: Int?,
                    @field:Json(name = "author_flair_template_id") val authorFlairTemplateId: Any?,
                    @field:Json(name = "is_original_content") val isOriginalContent: Boolean,
                    @field:Json(name = "user_reports") val userReports: List<Any>,
                    @field:Json(name = "secure_media") val secureMedia: Any?,
                    @field:Json(name = "is_reddit_media_domain") val isRedditMediaDomain: Boolean,
                    @field:Json(name = "is_meta") val isMeta: Boolean,
                    @field:Json(name = "category") val category: Any?,
                    @field:Json(name = "link_flair_text") val linkFlairText: String?,
                    @field:Json(name = "can_mod_post") val canModPost: Boolean,
                    @field:Json(name = "score") val score: Int,
                    @field:Json(name = "approved_by") val approvedBy: Any?,
                    @field:Json(name = "thumbnail") val thumbnail: String,
                    @field:Json(name = "edited") val edited: Any?,
                    @field:Json(name = "author_flair_css_class") val authorFlairCssClass: Any?,
                    @field:Json(name = "author_flair_richtext") val authorFlairRichtext: List<Any>,
                    @field:Json(name = "gildings") val gildings: Gildings,
                    @field:Json(name = "post_hint") val postHint: String,
                    @field:Json(name = "content_categories") val contentCategories: Any?,
                    @field:Json(name = "is_self") val isSelf: Boolean,
                    @field:Json(name = "mod_note") val modNote: Any?,
                    @field:Json(name = "created") val created: Int,
                    @field:Json(name = "link_flair_type") val linkFlairType: String,
                    @field:Json(name = "banned_by") val bannedBy: Any?,
                    @field:Json(name = "author_flair_type") val authorFlairType: String,
                    @field:Json(name = "contest_mode") val contestMode: Boolean,
                    @field:Json(name = "selftext_html") val selftextHtml: Any?,
                    @field:Json(name = "likes") val likes: Any?,
                    @field:Json(name = "suggested_sort") val suggestedSort: Any?,
                    @field:Json(name = "banned_at_utc") val bannedAtUtc: Any?,
                    @field:Json(name = "view_count") val viewCount: Any?,
                    @field:Json(name = "archived") val archived: Boolean,
                    @field:Json(name = "no_follow") val noFollow: Boolean,
                    @field:Json(name = "is_crosspostable") val isCrosspostable: Boolean,
                    @field:Json(name = "pinned") val pinned: Boolean,
                    @field:Json(name = "over_18") val over18: Boolean,
                    @field:Json(name = "preview") val preview: Preview?,
                    @field:Json(name = "media_only") val mediaOnly: Boolean,
                    @field:Json(name = "link_flair_template_id") val linkFlairTemplateId: Any?,
                    @field:Json(name = "can_gild") val canGild: Boolean,
                    @field:Json(name = "spoiler") val spoiler: Boolean,
                    @field:Json(name = "locked") val locked: Boolean,
                    @field:Json(name = "author_flair_text") val authorFlairText: Any?,
                    @field:Json(name = "visited") val visited: Boolean,
                    @field:Json(name = "num_reports") val numReports: Any?,
                    @field:Json(name = "distinguished") val distinguished: Any?,
                    @field:Json(name = "subreddit_id") val subredditId: String,
                    @field:Json(name = "mod_reason_by") val modReasonBy: Any?,
                    @field:Json(name = "removal_reason") val removalReason: Any?,
                    @field:Json(name = "link_flair_background_color") val linkFlairBackgroundColor: String,
                    @field:Json(name = "id") val id: String,
                    @field:Json(name = "is_robot_indexable") val isRobotIndexable: Boolean,
                    @field:Json(name = "report_reasons") val reportReasons: Any?,
                    @field:Json(name = "author") val author: String,
                    @field:Json(name = "num_crossposts") val numCrossposts: Int,
                    @field:Json(name = "num_comments") val numComments: Int,
                    @field:Json(name = "send_replies") val sendReplies: Boolean,
                    @field:Json(name = "whitelist_status") val whitelistStatus: String,
                    @field:Json(name = "mod_reports") val modReports: List<Any>,
                    @field:Json(name = "author_flair_text_color") val authorFlairTextColor: Any?,
                    @field:Json(name = "permalink") val permalink: String,
                    @field:Json(name = "parent_whitelist_status") val parentWhitelistStatus: String,
                    @field:Json(name = "stickied") val stickied: Boolean,
                    @field:Json(name = "url") val url: String,
                    @field:Json(name = "subreddit_subscribers") val subredditSubscribers: Int,
                    @field:Json(name = "created_utc") val createdUtc: Int,
                    @field:Json(name = "media") val media: Any?,
                    @field:Json(name = "is_video") val isVideo: Boolean
            ) {

                data class Preview(
                        @field:Json(name = "images") val images: List<Image>,
                        @field:Json(name = "enabled") val enabled: Boolean
                ) {

                    data class Image(
                            @field:Json(name = "source") val source: Source,
                            @field:Json(name = "resolutions") val resolutions: List<Resolution>,
                            @field:Json(name = "id") val id: String
                    ) {

                        data class Resolution(
                                @field:Json(name = "url") val url: String,
                                @field:Json(name = "width") val width: Int,
                                @field:Json(name = "height") val height: Int
                        )


                        data class Source(
                                @field:Json(name = "url") val url: String,
                                @field:Json(name = "width") val width: Int,
                                @field:Json(name = "height") val height: Int
                        )
                    }
                }


                data class Gildings(
                        @field:Json(name = "gid_1") val gid1: Int,
                        @field:Json(name = "gid_2") val gid2: Int,
                        @field:Json(name = "gid_3") val gid3: Int
                )
            }
        }
    }
}