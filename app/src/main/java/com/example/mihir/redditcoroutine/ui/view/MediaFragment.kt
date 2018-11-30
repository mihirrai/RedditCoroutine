package com.example.mihir.redditcoroutine.ui.view

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mihir.redditcoroutine.R
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.indicator.ProgressIndicator
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import kotlinx.android.synthetic.main.media_fragment.*

class MediaFragment : Fragment() {

    companion object {
        fun newInstance(fullMediaUrl: String): MediaFragment {
            val args = Bundle()
            args.putString("url", fullMediaUrl)
            val fragment = MediaFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: MediaViewModel
    private lateinit var url: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        url = arguments?.getString("url").toString()
        BigImageViewer.initialize(GlideImageLoader.with(context!!.applicationContext))
        return inflater.inflate(R.layout.media_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test.setImageViewFactory(GlideImageViewFactory())
        test.setProgressIndicator(object : ProgressIndicator {
            override fun onFinish() {
                progressBar.visibility = View.INVISIBLE
            }

            override fun getView(parent: BigImageView?): View {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onProgress(progress: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStart() {
                progressBar.visibility = View.VISIBLE
            }

        })
        test.showImage(Uri.parse(Html.fromHtml(url).toString()))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MediaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
