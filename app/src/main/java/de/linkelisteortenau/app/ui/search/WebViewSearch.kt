package de.linkelisteortenau.app.ui.search

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * WebView Search
 **/
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.webkit.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import de.linkelisteortenau.app.*
import de.linkelisteortenau.app.backend.connection.Connection
import de.linkelisteortenau.app.backend.share.Share
import de.linkelisteortenau.app.databinding.FragmentSearchBinding

/**
 * Class for the Brain WebView Content
 **/
class WebViewSearch(val context: Context) {
    private var webView: WebView? = null

    // Handler
    private val handler = Handler(Looper.getMainLooper())

    /**
     * Function to check Network Capabilities
     *
     * When no Connection to Network show the 404 Error and Toast
     * else show WebView
     *
     * @param requireActivity as Fragment Activity for check transport network capabilities
     * @param binding for WebView bindings
     * @param url the URL for WebView
     **/
    fun run(
        requireActivity: FragmentActivity,
        binding: FragmentSearchBinding,
        url: String
    ) {
        val progressBarText: TextView = binding.textViewProgressBar
        val progressBar: ProgressBar = binding.progressBarWebView
        val webView404Text: TextView = binding.textViewWebView404Text

        if (Connection(context).isOnline()) {
            runWebView(requireActivity, binding, url)
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.webview_error_no_connection_to_server),
                Toast.LENGTH_SHORT
            ).show()
            progressBar.isVisible = false
            progressBarText.isVisible = false
            webView404Text.isVisible = true
        }
    }

    /**
     * Function for the WebView content. Load from cache when no connection to the server can be established.
     * Scripts to ignore Cookie opt-in, Header, Header Image and Footer are included. If an error occurs the
     * 404 Text field are viewed.
     *
     * @param requireActivity for back press Button
     * @param binding for WebView bindings
     * @param url the URL for WebView
     **/
    @SuppressLint("SetJavaScriptEnabled")
    private fun runWebView(
        requireActivity: FragmentActivity,
        binding: FragmentSearchBinding,
        url: String
    ) {
        webView = binding.webView
        val progressBarText: TextView = binding.textViewProgressBar
        val progressBar: ProgressBar = binding.progressBarWebView
        val webView404Text: TextView = binding.textViewWebView404Text

        // This callback will only be called when Fragment is at least started.
        val callback = requireActivity.onBackPressedDispatcher.addCallback(requireActivity) {
            // Handle the back button event
            if (webView?.canGoBack() == true) {
                webView?.goBack()
            } else {
                // Code here
            }
        }
        callback.isEnabled

        // Java and store
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.domStorageEnabled = true
        webView!!.settings.cacheMode = WebSettings.LOAD_DEFAULT

        webView!!.loadUrl(url)

        webView!!.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                webView!!.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_COOKIE_NOTICE)
                webView!!.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_HEADER)
                webView!!.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_HEADER_FEATURED_IMAGE)
                webView!!.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_SEARCH)
                webView!!.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_SIDEBAR)
                webView!!.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_FOOTER)
            }

            /*
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Code here
            }*/

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBarText.isVisible = false
                progressBar.isVisible = false
                webView404Text.isVisible = false
                webView!!.isVisible = true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                progressBarText.isVisible = false
                progressBar.isVisible = false
                webView404Text.isVisible = true
                webView!!.isVisible = false

                if (!Connection(context).connectionToServer()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.webview_error_no_connection_to_server),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return if (request!!.url.toString().contains(HOST_URL_ORGANISATION)) {
                    super.shouldOverrideUrlLoading(view, request)
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    view!!.context.startActivity(intent)
                    true
                }
            }
        }

        // Run loading Text
        if (webView!!.progress < 100) {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val text =
                        "${context.getString(R.string.webview_loading)} ${webView!!.progress}${
                            context.getString(R.string.webview_percent)
                        }"
                    progressBarText.text = text
                    progressBar.progress = webView!!.progress
                    handler.postDelayed(this, 100)
                }
            }, 0)
        }

        /**
         * Share Content
         **/
        binding.fab.setOnClickListener {
            Share(context).shareContent(webView!!.url)
        }
    }
}