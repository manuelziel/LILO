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
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.databinding.ActivityPrivacyPolicyBinding

/**
 * Class Privacy Police as Activity
 **/
class PrivacyPolicy : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyPolicyBinding

    // Handler
    private val handler = Handler(Looper.getMainLooper())

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

        binding.progressBarPrivacyPolicy.isVisible = true
        binding.textViewProgressBarPrivacyPolicy.isVisible = true
        binding.textViewWebViewPrivacyPolicy404Text.isVisible = false
        binding.webViewPrivacyPolicy.isVisible = false
        binding.dividerPrivacyPolicy.isVisible = false
        binding.buttonAgree.isVisible = false
        binding.buttonDecline.isVisible = false

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
        val progressBarText: TextView = binding.textViewProgressBarPrivacyPolicy
        val progressBar: ProgressBar = binding.progressBarPrivacyPolicy
        val webView404Text: TextView = binding.textViewWebViewPrivacyPolicy404Text

        val divider = binding.dividerPrivacyPolicy
        val buttonAgree = binding.buttonAgree
        val buttonDecline = binding.buttonDecline

        webView.loadUrl(WEB_VIEW_URL_PRIVACY_POLICY)

        // Java and store
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

        webView.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                webView.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_COOKIE_NOTICE)
                webView.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_HEADER)
                webView.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_HEADER_FEATURED_IMAGE)
                webView.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_SIDEBAR)
                webView.loadUrl(WEB_VIEW_JAVASCRIPT_REMOVE_FOOTER)
            }

            /*
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // code here
            }*/

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBarText.isVisible = false
                progressBar.isVisible = false
                webView404Text.isVisible = false
                webView.isVisible = true
                divider.isVisible = true
                buttonAgree.isVisible = true
                buttonDecline.isVisible = true
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
                webView.isVisible = false
                divider.isVisible = true
                buttonAgree.isVisible = false
                buttonDecline.isVisible = true

                if (!Connection(baseContext).connectionToServer()) {
                    Toast.makeText(
                        baseContext,
                        getString(R.string.webview_error_no_connection_to_server),
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