package com.example.mihir.redditcoroutine.ui.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mihir.redditcoroutine.Injection
import com.example.mihir.redditcoroutine.R
import com.example.mihir.redditcoroutine.ui.ViewModelFactory
import com.example.mihir.redditcoroutine.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    lateinit var viewModel: LoginViewModel
    lateinit var viewModelFactory: ViewModelFactory

    val state = UUID.randomUUID().toString()
    val url = "https://www.reddit.com/api/v1/authorize.compact?" +
            "client_id=l3_rEXGA5nyt9A" +
            "&duration=permanent" +
            "&response_type=code" +
            "&state=$state" +
            "&redirect_uri=https://github.com/mihirrai&duration=permanent" +
            "&scope=identity edit flair history modconfig modflair modlog modposts modwiki mysubreddits privatemessages read report save submit subscribe vote wikiedit wikiread"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModelFactory = Injection.provideViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        webview_login.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String) {
                super.onPageFinished(view, url)
                if (url.contains("&code=") || url.contains("?code=")) {
                    val uri = Uri.parse(url)
                    if (uri.getQueryParameter("state") == state) {
                        val authCode = uri.getQueryParameter("code")
                        viewModel.authCodeToAccessToken(authCode!!)
                    }
                }
            }
        }
        webview_login.loadUrl(url)
        viewModel.success.observe(this, androidx.lifecycle.Observer {
            if (it)
                setResult(Activity.RESULT_OK)
            else
                setResult(Activity.RESULT_CANCELED)
            finish()
        })
    }
}
