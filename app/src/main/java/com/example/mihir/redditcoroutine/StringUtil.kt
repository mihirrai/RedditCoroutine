package com.example.mihir.redditcoroutine

import android.content.Context
import android.text.SpannableStringBuilder
import android.widget.TextView
import com.example.mihir.redditcoroutine.data.local.entity.PostEntity
import com.example.mihir.redditcoroutine.data.remote.PostDetailResponse
import ru.noties.markwon.Markwon
import ru.noties.markwon.SpannableConfiguration
import ru.noties.markwon.renderer.SpannableRenderer


interface StringUtil {
    fun getPostDetails(postEntity: PostEntity, context: Context): SpannableStringBuilder {
        val detailStringBuilder = SpannableStringBuilder()
        val separator = " \u2022 "
        if (postEntity.nsfw)
            detailStringBuilder.append("NSFW$separator")
        if (postEntity.flair != null)
            detailStringBuilder.append(postEntity.flair + separator)
        detailStringBuilder.append(postEntity.author + separator +
                postEntity.subreddit + separator +
                getTimeAgo(postEntity.createdUtc.toLong() * 1000))
        return detailStringBuilder
    }

    fun getCommentDetails(children: PostDetailResponse.Data.Children): SpannableStringBuilder {

        val detailsStringBuilder = SpannableStringBuilder()
        val separator = " \u2022 "
        return detailsStringBuilder.append(children.data.author + separator +
                children.data.score + separator +
                getTimeAgo(children.data.createdUtc.toLong() * 1000))
    }

    fun getPostStats(postEntity: PostEntity): SpannableStringBuilder {
        val statStringBuilder = SpannableStringBuilder()
        statStringBuilder.append(withSuffix(postEntity.score) + " points" + "\n" +
                postEntity.numComments + " comments")
        return statStringBuilder
    }

    fun getMarkDown(text: String, context: Context, view: TextView) {
        val parser = Markwon.createParser()
        val configuration = SpannableConfiguration.create(context)
        val renderer = SpannableRenderer()
        val node = parser.parse(text)
        val text = renderer.render(configuration, node)
        Markwon.unscheduleDrawables(view)
        Markwon.unscheduleTableRows(view)
        view.text = text
        Markwon.scheduleDrawables(view)
        Markwon.scheduleTableRows(view)
    }

fun withSuffix(count: Int): String {
    return when {
        count < 10000 -> count.toString()
        count < 100000 -> (count / 1000).toString() + "k"
        else -> (count / 1000).toString()
    }
}

fun getTimeAgo(time: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - time
    return when {
        diff < (60 * 1000) -> "just now"
        diff < (2 * 60 * 1000) -> "a minute ago"
        diff < (60 * 60 * 1000) -> (diff / (60 * 1000)).toString() + " minutes ago"
        diff < (120 * 60 * 1000) -> "an hour ago"
        diff < (24 * 60 * 60 * 1000) -> (diff / (60 * 60 * 1000)).toString() + " hours ago"
        (diff / (24 * 60 * 60 * 1000)) < 2 -> "1 day ago"
        (diff / (24 * 60 * 60 * 1000)) < 365 -> (diff / (24 * 60 * 60 * 1000)).toString() + " days ago"
        ((diff / (24 * 60 * 60 * 1000)) / 365) < 2 -> "an year ago"
        else -> ((diff / 24 * 60 * 60 * 1000) / 365).toString() + " years ago"
    }
}
}