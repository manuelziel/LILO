package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Invitation link to Signal Profile
 **/
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import de.linkelisteortenau.app.INSTAGRAM_PAGE_ID

/**
 * Class for Instagram Profile
 *
 * @param context as Context
 **/
class Instagram(
    val context: Context
) {

    /**
     * Function to open the Instagram app with the profile link,
     * or redirect to the mobile website if the app is not installed.
     **/
    fun openInstagramProfile() {
        redirectToInstagramMobileWebsite()
    }

    /**
     * Method to redirect to the mobile website if the Instagram app is not installed
     */
    private fun redirectToInstagramMobileWebsite() {
        val instagramUrl = "https://www.instagram.com/$INSTAGRAM_PAGE_ID"
        val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
        context.startActivity(websiteIntent)
    }
}