package de.linkelisteortenau.app.ui.dialog

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Dialog for the App Reset
 **/
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.backend.articles.Articles
import de.linkelisteortenau.app.backend.events.Events
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Dialog class
 **/
class DialogReset : DialogFragment() {

    /**
     * When pressed yes than delete all Preferences, Complete Article SQL-Database
     * and Event SQL-Database
     *
     * @see <a href="https://developer.android.com/develop/ui/views/components/dialogs#AlertDialog">Dialogs</a>
     */
    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog {
        return activity?.let { it ->
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(getString(R.string.dialog_reset_message))

                // YES Button
                .setPositiveButton(getString(R.string.dialog_action_yes),
                    DialogInterface.OnClickListener { _, _ ->
                        Preferences(it).deleteAllPreferences()
                        Events(it).deleteDatabase()
                        Articles(it).deleteDatabase()
                        requireActivity().finishAffinity()
                    })

                // NO Button
                .setNegativeButton(getString(R.string.dialog_action_no),
                    DialogInterface.OnClickListener { _, _ ->
                        // Code here
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}