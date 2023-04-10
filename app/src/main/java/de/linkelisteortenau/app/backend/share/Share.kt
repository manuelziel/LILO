package de.linkelisteortenau.app.backend.share

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Class for share content
 **/
import android.content.Context
import android.content.Intent
import android.util.Log
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.backend.debug.DEBUG_SHARE
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Wrapper class for sharing content via an Intent
 *
 * @param context the context in which the intent will be launched
 **/
class Share(
    private val context: Context
    ) {

    /**
     * Shares content via an Intent
     *
     * @param url the URL of the content to share
     **/
    fun shareContent(
        url: String?
    ) {
        url ?: return

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject))
        val shareMessage = "${context.getString(R.string.share_message_show_here)}\n\n$url"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)

        try {
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_chooser_title)))
        } catch (e: Exception) {
            if (Preferences(context).getSystemDebug()) {
                Log.w(DEBUG_SHARE, "Failed to share content: ${e.message}")
            }
        }
    }
}