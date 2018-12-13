package com.example.mihir.redditcoroutine.ui.view

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.mihir.redditcoroutine.R
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.loader.glide.GlideImageLoader
import kotlinx.android.synthetic.main.activity_image.*
import me.saket.flick.ContentSizeProvider
import me.saket.flick.FlickCallbacks
import me.saket.flick.FlickGestureListener
import me.saket.flick.InterceptResult
import java.io.File

class ImageActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, imageUrl: String): Intent {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("url", imageUrl)
            return intent
        }
    }

    private fun flickGestureListener(): FlickGestureListener {
        val contentHeightProvider = object : ContentSizeProvider {
            override fun heightForDismissAnimation(): Int {
                return test.height
            }

            // A positive height value is important so that the user
            // can dismiss even while the progress indicator is visible.
            override fun heightForCalculatingDismissThreshold(): Int {
                return when {
                    test.height == 0 -> resources.getDimensionPixelSize(R.dimen.mediaalbumviewer_image_height_when_empty)
                    else -> test.measuredHeight
                }
            }
        }

        val callbacks = object : FlickCallbacks {
            override fun onFlickDismiss(flickAnimationDuration: Long) {
                finishInMillis(flickAnimationDuration)
            }

            override fun onMove(@FloatRange(from = -1.0, to = 1.0) moveRatio: Float) {
                updateBackgroundDimmingAlpha(Math.abs(moveRatio))
            }
        }

        val gestureListener = FlickGestureListener(this, contentHeightProvider, callbacks)

        // Block flick gestures if the image can pan further.
        gestureListener.gestureInterceptor = { scrollY ->
            val isScrollingUpwards = scrollY < 0
            val directionInt = if (isScrollingUpwards) -1 else +1
            val canPanFurther = test.canScrollVertically(directionInt)

            when {
                canPanFurther -> InterceptResult.INTERCEPTED
                else -> InterceptResult.IGNORED
            }
        }

        return gestureListener
    }

    private lateinit var activityBackgroundDrawable: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        BigImageViewer.initialize(GlideImageLoader.with(this.applicationContext))
        setContentView(R.layout.activity_image)
        animateDimmingOnEntry()
        loadImage()
        imageviewer_image_container.gestureListener = flickGestureListener()
    }

    fun loadImage() {
        test.showImage(Uri.parse(intent.getStringExtra("url")))
        test.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onFinish() {
                imageviewer_progress.visibility = View.INVISIBLE
            }

            override fun onSuccess(image: File?) {
                imageviewer_progress.visibility = View.INVISIBLE
            }

            override fun onFail(error: Exception?) {
                imageviewer_progress.visibility = View.INVISIBLE
            }

            override fun onCacheHit(imageType: Int, image: File?) {
                imageviewer_progress.visibility = View.INVISIBLE
            }

            override fun onCacheMiss(imageType: Int, image: File?) {
                imageviewer_progress.visibility = View.VISIBLE
            }

            override fun onProgress(progress: Int) {
                imageviewer_progress.progress = progress
            }

            override fun onStart() {
                imageviewer_progress.visibility = View.VISIBLE
            }

        })
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        animateExit {
            super.onBackPressed()
        }
    }

    private fun animateExit(onEndAction: () -> Unit) {
        val animDuration: Long = 200
        imageviewer_image_container.animate()
                .alpha(0f)
                .translationY(imageviewer_image_container.height / 20F)
                .rotation(-2F)
                .setDuration(animDuration)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction(onEndAction)
                .start()

        ObjectAnimator.ofFloat(0F, 1F).apply {
            duration = animDuration
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener { animation ->
                updateBackgroundDimmingAlpha(animation.animatedValue as Float)
            }
            start()
        }
    }

    private fun finishInMillis(millis: Long) {
        imageviewer_root.postDelayed({ finish() }, millis)
    }

    private fun updateBackgroundDimmingAlpha(@FloatRange(from = 0.0, to = 1.0) transparencyFactor: Float) {
        // Increase dimming exponentially so that the background is
        // fully transparent while the image has been moved by half.
        val dimming = 1f - Math.min(1f, transparencyFactor * 2)
        activityBackgroundDrawable.alpha = (dimming * 255).toInt()
    }

    private fun animateDimmingOnEntry() {
        activityBackgroundDrawable = imageviewer_root.background.mutate()
        imageviewer_root.background = activityBackgroundDrawable

        ObjectAnimator.ofFloat(1F, 0f).apply {
            duration = 200
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener { animation ->
                updateBackgroundDimmingAlpha(animation.animatedValue as Float)
            }
            start()
        }
    }
}
