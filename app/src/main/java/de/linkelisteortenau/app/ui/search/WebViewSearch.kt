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
class WebViewSearch(
    val context: Context
    ) {

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
            progressBar.isVisible       = false
            progressBarText.isVisible   = false
            webView404Text.isVisible    = true
        }
    }

    /**
     * Function for the WebView content. Load from cache when no connection to the server can be established.
     * Scripts to ignore Cookie opt-in, Header, Header Image and Footer are included. If an error occurs the
     * 404 Text field are viewed.
     *
     * @param activity for back press Button
     * @param binding for WebView bindings
     * @param url the URL for WebView
     **/
    @SuppressLint("SetJavaScriptEnabled")
    private fun runWebView(
        activity: FragmentActivity,
        binding: FragmentSearchBinding,
        url: String
    ) {
        val webView = binding.webView
        val handler = Handler(Looper.getMainLooper())
        val progressBarText: TextView = binding.textViewProgressBar
        val progressBar: ProgressBar = binding.progressBarWebView
        val webView404Text: TextView = binding.textViewWebView404Text

        // This callback will only be called when Fragment is at least started.
        activity.onBackPressedDispatcher.addCallback(activity) {
            // Handle the back button event
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                // Code here
            }
        }.isEnabled

        // Enable JavaScript and store
        webView.settings.apply {
            javaScriptEnabled               = true
            //loadsImagesAutomatically      = true
            cacheMode                       = WebSettings.LOAD_DEFAULT
            domStorageEnabled               = true
            //loadWithOverviewMode          = true
            //useWideViewPort               = true
            databaseEnabled                 = true
            //allowFileAccess               = true
            //allowContentAccess            = true
            //mixedContentMode              = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            //defaultTextEncodingName       = "utf-8"
        }
        webView.loadUrl(url)

        // Set up a WebViewClient to handle events related to a WebView
        webView.webViewClient = object : WebViewClient() {

            // This method is called when a page finishes loading in the WebView
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                // Execute a Javascript command to remove content from the loaded page
                webView.evaluateJavascript(WEB_VIEW_JAVASCRIPT_REMOVE_CONTENT) { _ ->
                    progressBarText.isVisible   = false
                    progressBar.isVisible       = false
                    webView404Text.isVisible    = false
                    webView.isVisible           = true
                }

                webView.evaluateJavascript(WEB_VIEW_JAVASCRIPT_REMOVE_SEARCH) {}
                webView.evaluateJavascript(WEB_VIEW_JAVASCRIPT_REMOVE_SIDEBAR) {}
                webView.evaluateJavascript(WEB_VIEW_JAVASCRIPT_REMOVE_FOOTER) {}
            }

            // This method is called when an error occurs while loading a page in the WebView
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                progressBarText.isVisible       = false
                progressBar.isVisible           = false
                webView404Text.isVisible        = true
                webView.isVisible               = false

                // If there's no internet connection, show a toast message to the user
                if (!Connection(context).connectionToServer()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.webview_error_no_connection_to_server),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // This method is called when a new URL request is about to be loaded in the WebView
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // If the requested URL belongs to the HOST_URL_ORGANISATION domain, let the WebView handle it
                return if (request!!.url.toString().contains(HOST_URL_ORGANISATION)) {
                    super.shouldOverrideUrlLoading(view, request)
                }
                // Otherwise, open the URL in an external browser
                else {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    view!!.context.startActivity(intent)
                    true
                }
            }
        }

        // Start showing a loading progress indicator, with an update interval of 100ms
        if (webView.progress < 100) {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val text =
                        "${context.getString(R.string.webview_loading)} ${webView.progress}${
                            context.getString(R.string.webview_percent)
                        }"
                    progressBarText.text = text
                    progressBar.progress = webView.progress
                    handler.postDelayed(this, 100)
                }
            }, 0)
        }

        /**
         * Share Content
         **/
        binding.fab.setOnClickListener {
            Share(context).shareContent(webView.url)
        }
    }
}