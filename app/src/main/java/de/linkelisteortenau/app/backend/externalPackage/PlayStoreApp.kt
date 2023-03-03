package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Link to this PlayStore App
 **/
import android.content.Context
import android.content.Intent
import de.linkelisteortenau.app.BuildConfig

/**
 * Class to open this App in PlayStore
 *
 * @param context as Context
 **/
class PlayStoreApp(val context: Context) {

    /**
     * Share Function to open the Google PlayStore with this App
     **/
    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Linke Liste Ortenau")
            var shareMessage = "\n Hier das App der Linken Liste Ortenau: \n\n"
            shareMessage =
                """
    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
    """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }
}