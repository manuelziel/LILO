package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Link to Facebook Profile
 **/
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import de.linkelisteortenau.app.*

/**
 * Class for Facebook Profile
 *
 * @param context as Context
 **/
class Facebook(
    val context: Context
) {

    /**
     * Function to open the Facebook app with the group link,
     * or redirect to the mobile website if the app is not installed.
     **/
    fun openFacebookProfile() {
        redirectToFacebookMobileWebsite()
    }

    /**
     * Method to redirect to the mobile website if the Facebook app is not installed
     */
    private fun redirectToFacebookMobileWebsite() {
        val facebookUrl = FACEBOOK_GROUP_URL
        val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        context.startActivity(websiteIntent)
    }
}