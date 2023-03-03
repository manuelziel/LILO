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
class MailTo(val context: Context) {

    /**
     * Function to open the E-Mail app, write to Organisation address with Subject and Text
     **/
    fun performEmail() {
        val emailAddress = context.getString(R.string.contact_mail_address)
        val emailSubject = context.getString(R.string.feedback_mail_subject)
        val emailTxt = context.getString(R.string.contact_mail_text)

        val intentEmail = Intent(Intent.ACTION_SEND, Uri.fromParts("mailto", emailAddress, null))
        //Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null))
        intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        intentEmail.putExtra(Intent.EXTRA_TEXT, emailTxt)
        intentEmail.type = "message/rfc822"
        context.startActivity(intentEmail)
    }
}