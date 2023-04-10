package de.linkelisteortenau.app.ui.preferences

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * View and set the Privacy Policy for the hole app
 **/
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import de.linkelisteortenau.app.*
import de.linkelisteortenau.app.backend.connection.Connection
import de.linkelisteortenau.app.backend.defaults.Defaults
import de.linkelisteortenau.app.databinding.ActivityPrivacyPolicyBinding

/**
 * Class Privacy Police as Activity
 **/
class PrivacyPolicy : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyPolicyBinding

    /**
     * Lifecycle
     *
     * Activity lifecycle create
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     **/
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.title_privacy_policy)

        // View web content from privacy policy
        runWebView()

        val buttonYes: Button = binding.buttonAgree
        val buttonNo: Button = binding.buttonDecline

        binding.progressBarPrivacyPolicy.isVisible              = true
        binding.textViewProgressBarPrivacyPolicy.isVisible      = true
        binding.textViewWebViewPrivacyPolicy404Text.isVisible   = false
        binding.webViewPrivacyPolicy.isVisible                  = false
        binding.dividerPrivacyPolicy.isVisible                  = false
        binding.buttonAgree.isVisible                           = false
        binding.buttonDecline.isVisible                         = false

        // Button agree
        buttonYes.setOnClickListener {
            performAgree()
        }

        // Button decline
        buttonNo.setOnClickListener {
            performDecline()
        }
    }

    /**
     * Lifecycle
     *
     * Activity lifecycle destroy
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     **/
    override fun onDestroy() {
        super.onDestroy()
        this.finishAffinity()
    }

    /**
     * Function for show the privacy policy content from the organisations web content
     **/
    @SuppressLint("SetJavaScriptEnabled")
    private fun runWebView() {
        val webView: WebView = binding.webViewPrivacyPolicy
        val handler = Handler(Looper.getMainLooper())
        val progressBarText: TextView   = binding.textViewProgressBarPrivacyPolicy
        val progressBar: ProgressBar    = binding.progressBarPrivacyPolicy
        val webView404Text: TextView    = binding.textViewWebViewPrivacyPolicy404Text

        val divider         = binding.dividerPrivacyPolicy
        val buttonAgree     = binding.buttonAgree
        val buttonDecline   = binding.buttonDecline

        // Enable JavaScript and store
        webView.settings.apply {
            javaScriptEnabled           = true
            //loadsImagesAutomatically  = true
            cacheMode                   = WebSettings.LOAD_DEFAULT
            domStorageEnabled           = true
            //loadWithOverviewMode      = true
            //useWideViewPort           = true
            databaseEnabled             = true
            //allowFileAccess           = true
            //allowContentAccess        = true
            //mixedContentMode          = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            //defaultTextEncodingName   = "utf-8"
        }
        webView.loadUrl(WEB_VIEW_URL_PRIVACY_POLICY)

        webView.webViewClient = object : WebViewClient() {
            // Execute a Javascript command to remove content from the loaded page
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                webView.evaluateJavascript(WEB_VIEW_JAVASCRIPT_REMOVE_CONTENT) { _ ->
                    progressBarText.isVisible   = false
                    progressBar.isVisible       = false
                    webView404Text.isVisible    = false
                    webView.isVisible           = true
                    divider.isVisible           = true
                    buttonAgree.isVisible       = true
                    buttonDecline.isVisible     = true
                }
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
                divider.isVisible               = true
                buttonAgree.isVisible           = false
                buttonDecline.isVisible         = true

                // If there's no internet connection, show a toast message to the user
                if (!Connection(baseContext).connectionToServer()) {
                    Toast.makeText(
                        baseContext,
                        getString(R.string.webview_error_no_connection_to_server),
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
                        "${getString(R.string.webview_loading)} ${webView.progress}${getString(R.string.webview_percent)}"
                    progressBarText.text = text
                    progressBar.progress = webView.progress
                    handler.postDelayed(this, 100)
                }
            }, 0)
        }
    }

    /**
     * Set preference privacy policy to true.
     **/
    private fun performAgree() {
        // Set Defaults and Privacy Policy
        Defaults(this).setDefaults()

        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this, 1, intent,
            PendingIntent.FLAG_MUTABLE
        )
        pIntent.send()
    }

    /**
     * Cancel and exit the APP without privacy settings
     **/
    private fun performDecline() {
        this.finishAffinity()
    }
}