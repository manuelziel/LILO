package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Invitation link to Telegram Group
 **/
import android.content.Context
import android.content.Intent
import android.net.Uri
import de.linkelisteortenau.app.TELEGRAM_GROUP_URL

/**
 * Class for Telegram Group
 *
 * @param context as Context
 **/
class Telegram(val context: Context) {

    /**
     * Function to open the Telegram app with the invitation link to the Group
     **/
    fun performToGroupLink() {
        val uri = Uri.parse(TELEGRAM_GROUP_URL)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        //telegram.setPackage("org.telegram.messenger")
        context.startActivity(intent)
    }
}