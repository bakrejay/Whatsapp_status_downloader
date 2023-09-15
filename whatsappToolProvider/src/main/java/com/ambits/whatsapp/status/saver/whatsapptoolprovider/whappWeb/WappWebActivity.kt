package com.ambits.whatsapp.status.saver.whatsapptoolprovider.whappWeb

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.R
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.databinding.ActivityWappWebBinding

class WappWebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWappWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWappWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            flWebViewContainer.addWappWebView()
            ivBack.setOnClickListener { finish() }
            ivReload.setOnClickListener {
                flWebViewContainer.addWappWebView()
            }
        }
    }

    fun FrameLayout.addWappWebView(){
        this.apply {
            val webView = WebView(this@WappWebActivity)
            webView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            removeAllViews()
            addView(webView)
            webView.loadWebWapp()
        }
    }

    fun WebView.loadWebWapp() {
        this.apply {
            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    showProgress()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    hideProgress()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?,
                ) {
                    hideProgress()
                }
            }
            loadUrl("https://web.whatsapp.com/")
            settings.userAgentString =
                "Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0"
            settings.setGeolocationEnabled(true)
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.setNeedInitialFocus(true)
            settings.loadWithOverviewMode = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.blockNetworkImage = true
            settings.builtInZoomControls = false

            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
            //for Speed up downloading and rendering
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
            settings.useWideViewPort = true
            settings.saveFormData = true
            settings.setEnableSmoothTransition(true)
            setInitialScale(100)
        }
    }

    private fun showProgress() {
        binding.flLoading.isVisible = true
    }

    private fun hideProgress() {
        binding.flLoading.isVisible = false
    }

    override fun onStart() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.wapp_status_bar_color)
        super.onStart()
    }

}