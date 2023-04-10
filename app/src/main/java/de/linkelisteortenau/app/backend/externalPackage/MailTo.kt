package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * To Mail the Organisation
 **/
import android.content.Context
import android.content.Intent
import android.net.Uri
import de.linkelisteortenau.app.R

/**
 * Class to mail the Organisation
 *
 * @param context as Context
 **/
class MailTo(
    val context: Context
    ) {

    /**
     * Function to open the E-Mail app, write to Organisation address with Subject and Text
     **/
    fun performEmail() {
        // Get the email address, subject and text from resources
        val emailAddress = context.getString(R.string.contact_mail_address)
        val emailSubject = context.getString(R.string.feedback_mail_subject)
        val emailTxt = context.getString(R.string.contact_mail_text)

        // Create a new email intent with the recipient email address and the email type

        val intentEmail = Intent(Intent.ACTION_SEND, Uri.fromParts("mailto", emailAddress, null))
        //Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null))

        // Add the email address, subject and text as extras to the intent
        intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        intentEmail.putExtra(Intent.EXTRA_TEXT, emailTxt)

        // Set the email type to "message/rfc822" to ensure that the email app is used to handle the intent
        intentEmail.type = "message/rfc822"

        // Start the email activity
        context.startActivity(intentEmail)
    }
}