package com.example.mihir.redditcoroutine.data.remote.response

import com.squareup.moshi.Json

data class SubredditListResponse(
        @field:Json(name = "data")
        val `data`: Data,
        @field:Json(name = "kind")
        val kind: String
) {
    data class Data(
            @field:Json(name = "after")
            val after: String,
            @field:Json(name = "before")
            val before: Any?,
            @field:Json(name = "children")
            val children: List<Children>,
            @field:Json(name = "dist")
            val dist: Int,
            @field:Json(name = "modhash")
            val modhash: String
    ) {
        data class Children(
                @field:Json(name = "data")
                val `data`: Data,
                @field:Json(name = "kind")
                val kind: String
        ) {
            data class Data(
                    @field:Json(name = "accounts_active")
                    val accountsActive: Any?,
                    @field:Json(name = "accounts_active_is_fuzzed")
                    val accountsActiveIsFuzzed: Boolean,
                    @field:Json(name = "active_user_count")
                    val activeUserCount: Any?,
                    @field:Json(name = "advertiser_category")
                    val advertiserCategory: String,
                    @field:Json(name = "all_original_content")
                    val allOriginalContent: Boolean,
                    @field:Json(name = "allow_discovery")
                    val allowDiscovery: Boolean,
                    @field:Json(name = "allow_images")
                    val allowImages: Boolean,
                    @field:Json(name = "allow_videogifs")
                    val allowVideogifs: Boolean,
                    @field:Json(name = "allow_videos")
                    val allowVideos: Boolean,
                    @field:Json(name = "banner_background_color")
                    val bannerBackgroundColor: String,
                    @field:Json(name = "banner_background_image")
                    val bannerBackgroundImage: String,
                    @field:Json(name = "banner_img")
                    val bannerImg: String,
                    @field:Json(name = "banner_size")
                    val bannerSize: Any?,
                    @field:Json(name = "can_assign_link_flair")
                    val canAssignLinkFlair: Boolean,
                    @field:Json(name = "can_assign_user_flair")
                    val canAssignUserFlair: Boolean,
                    @field:Json(name = "collapse_deleted_comments")
                    val collapseDeletedComments: Boolean,
                    @field:Json(name = "comment_score_hide_mins")
                    val commentScoreHideMins: Int,
                    @field:Json(name = "community_icon")
                    val communityIcon: String,
                    @field:Json(name = "created")
                    val created: Int,
                    @field:Json(name = "created_utc")
                    val createdUtc: Int,
                    @field:Json(name = "description")
                    val description: String,
                    @field:Json(name = "description_html")
                    val descriptionHtml: String,
                    @field:Json(name = "display_name")
                    val displayName: String,
                    @field:Json(name = "display_name_prefixed")
                    val displayNamePrefixed: String,
                    @field:Json(name = "emojis_custom_size")
                    val emojisCustomSize: Any?,
                    @field:Json(name = "emojis_enabled")
                    val emojisEnabled: Boolean,
                    @field:Json(name = "free_form_reports")
                    val freeFormReports: Boolean,
                    @field:Json(name = "has_menu_widget")
                    val hasMenuWidget: Boolean,
                    @field:Json(name = "header_img")
                    val headerImg: String,
                    @field:Json(name = "header_size")
                    val headerSize: List<Int>,
                    @field:Json(name = "header_title")
                    val headerTitle: String,
                    @field:Json(name = "hide_ads")
                    val hideAds: Boolean,
                    @field:Json(name = "icon_img")
                    val iconImg: String,
                    @field:Json(name = "icon_size")
                    val iconSize: List<Int>,
                    @field:Json(name = "id")
                    val id: String,
                    @field:Json(name = "is_enrolled_in_new_modmail")
                    val isEnrolledInNewModmail: Any?,
                    @field:Json(name = "key_color")
                    val keyColor: String,
                    @field:Json(name = "lang")
                    val lang: String,
                    @field:Json(name = "link_flair_enabled")
                    val linkFlairEnabled: Boolean,
                    @field:Json(name = "link_flair_position")
                    val linkFlairPosition: String,
                    @field:Json(name = "name")
                    val name: String,
                    @field:Json(name = "notification_level")
                    val notificationLevel: Any?,
                    @field:Json(name = "original_content_tag_enabled")
                    val originalContentTagEnabled: Boolean,
                    @field:Json(name = "over18")
                    val over18: Boolean,
                    @field:Json(name = "primary_color")
                    val primaryColor: String,
                    @field:Json(name = "public_description")
                    val publicDescription: String,
                    @field:Json(name = "public_description_html")
                    val publicDescriptionHtml: String,
                    @field:Json(name = "public_traffic")
                    val publicTraffic: Boolean,
                    @field:Json(name = "quarantine")
                    val quarantine: Boolean,
                    @field:Json(name = "show_media")
                    val showMedia: Boolean,
                    @field:Json(name = "show_media_preview")
                    val showMediaPreview: Boolean,
                    @field:Json(name = "spoilers_enabled")
                    val spoilersEnabled: Boolean,
                    @field:Json(name = "submission_type")
                    val submissionType: String,
                    @field:Json(name = "submit_link_label")
                    val submitLinkLabel: Any?,
                    @field:Json(name = "submit_text")
                    val submitText: String,
                    @field:Json(name = "submit_text_html")
                    val submitTextHtml: String,
                    @field:Json(name = "submit_text_label")
                    val submitTextLabel: Any?,
                    @field:Json(name = "subreddit_type")
                    val subredditType: String,
                    @field:Json(name = "subscribers")
                    val subscribers: Int,
                    @field:Json(name = "suggested_comment_sort")
                    val suggestedCommentSort: Any?,
                    @field:Json(name = "title")
                    val title: String,
                    @field:Json(name = "url")
                    val url: String,
                    @field:Json(name = "user_can_flair_in_sr")
                    val userCanFlairInSr: Any?,
                    @field:Json(name = "user_flair_background_color")
                    val userFlairBackgroundColor: Any?,
                    @field:Json(name = "user_flair_css_class")
                    val userFlairCssClass: Any?,
                    @field:Json(name = "user_flair_enabled_in_sr")
                    val userFlairEnabledInSr: Boolean,
                    @field:Json(name = "user_flair_position")
                    val userFlairPosition: String,
                    @field:Json(name = "user_flair_richtext")
                    val userFlairRichtext: List<Any>,
                    @field:Json(name = "user_flair_template_id")
                    val userFlairTemplateId: Any?,
                    @field:Json(name = "user_flair_text")
                    val userFlairText: Any?,
                    @field:Json(name = "user_flair_text_color")
                    val userFlairTextColor: Any?,
                    @field:Json(name = "user_flair_type")
                    val userFlairType: String,
                    @field:Json(name = "user_has_favorited")
                    val userHasFavorited: Any?,
                    @field:Json(name = "user_is_banned")
                    val userIsBanned: Any?,
                    @field:Json(name = "user_is_contributor")
                    val userIsContributor: Any?,
                    @field:Json(name = "user_is_moderator")
                    val userIsModerator: Any?,
                    @field:Json(name = "user_is_muted")
                    val userIsMuted: Any?,
                    @field:Json(name = "user_is_subscriber")
                    val userIsSubscriber: Any?,
                    @field:Json(name = "user_sr_flair_enabled")
                    val userSrFlairEnabled: Any?,
                    @field:Json(name = "user_sr_theme_enabled")
                    val userSrThemeEnabled: Boolean,
                    @field:Json(name = "whitelist_status")
                    val whitelistStatus: String,
                    @field:Json(name = "wiki_enabled")
                    val wikiEnabled: Boolean,
                    @field:Json(name = "wls")
                    val wls: Int
            )
        }
    }
}