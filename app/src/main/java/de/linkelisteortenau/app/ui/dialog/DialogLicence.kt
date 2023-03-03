package de.linkelisteortenau.app.ui.dialog

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Dialog for the Licence
 **/
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import de.linkelisteortenau.app.R
import java.util.*

/**
 * Dialog class
 *
 * @see <a href="https://developer.android.com/develop/ui/views/components/dialogs#AlertDialog">Dialogs</a>
 **/
class DialogLicence : DialogFragment() {

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog {
        val message = getString(R.string.dialog_license_copyright_message) + " ${Calendar.getInstance().get(Calendar.YEAR)} " +
                getString(R.string.global_app_company_name) + "\n\n" +
                getString(R.string.dialog_license_message) + "\n\n" +
                getString(R.string.dialog_license_link) + "\n\n" +
                getString(R.string.dialog_license_message_last)

        return activity?.let { it ->
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(getString(R.string.dialog_license_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.dialog_action_ok),
                    DialogInterface.OnClickListener { _, _ ->
                        // CODE
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}