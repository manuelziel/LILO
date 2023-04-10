package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Link to this PlayStore App
 **/
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import de.linkelisteortenau.app.BuildConfig
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.backend.debug.DEBUG_SHARE

/**
 * Class to open this App in PlayStore
 *
 * @param context as Context
 **/
class ShareApp(
    val context: Context
) {

    /**
     * Function to open the Google PlayStore and share this App
     **/
    fun share() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_app_subject))
            val shareMessage = "${context.getString(R.string.share_app_message)}\n\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
            putExtra(Intent.EXTRA_TEXT, shareMessage)
        }
        try {
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_app_title)))
        } catch (e: ActivityNotFoundException) {
            Log.e(DEBUG_SHARE, "Error sharing app: ${e.message}")
            Toast.makeText(context, context.getString(R.string.share_app_error), Toast.LENGTH_SHORT).show() // Fehlermeldung für Benutzer
        }
    }
}