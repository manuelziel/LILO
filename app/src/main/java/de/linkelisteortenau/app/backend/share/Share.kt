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
 * Class for share content
 *
 * @param context as Context
 **/
class Share(val context: Context) {

    /**
     * Function for share content
     *
     * @param url as URL of the content link
     **/
    fun shareContent(url: String?) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject))
            var shareMessage = "\n ${context.getString(R.string.share_message_show_here)} \n\n"
            shareMessage =
                """
                ${shareMessage}${url}
                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_chooser_title)))
        } catch (e: Exception) {
            if (Preferences(context).getSystemDebug()) {
                Log.w(DEBUG_SHARE, e.toString())
            }
        }
    }
}