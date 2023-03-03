package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Invitation link to Signal Group
 **/
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.SIGNAL_GROUP_URL

/**
 * Class for Signal Group
 *
 * @param context as Context
 **/
class Signal(val context: Context) {

    /**
     * Function to open the Signal app with the invitation link to the Group
     **/
    fun performToGroupLink() {
        val uri = Uri.parse(SIGNAL_GROUP_URL)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}