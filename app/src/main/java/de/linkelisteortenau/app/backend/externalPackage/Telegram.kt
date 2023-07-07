package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Invitation link to Telegram Profile
 **/
import android.content.Context
import android.content.Intent
import android.net.Uri
import de.linkelisteortenau.app.TELEGRAM_GROUP_URL

/**
 * Class for Telegram Profile
 *
 * @param context The context in which the Telegram instance is created
 **/
class Telegram(
    val context: Context
    ) {

    /**
     * Function to open the Telegram app with the invitation link to the Group
     **/
    fun openTelegramProfile() {
        // Create a Uri object from the Telegram group URL
        val uri = Uri.parse(TELEGRAM_GROUP_URL)
        // Create an Intent object with the ACTION_VIEW action and the Uri object
        val intent = Intent(Intent.ACTION_VIEW, uri)
        // Set the package name to "org.telegram.messenger" to ensure that the Telegram app is used
        //intent.setPackage("org.telegram.messenger")
        // Start the activity with the intent
        context.startActivity(intent)
    }
}