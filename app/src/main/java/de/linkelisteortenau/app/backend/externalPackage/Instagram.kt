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
class Instagram (
    val context: Context
) {

    /**
     * Function to open the Instagram app with the profile link,
     * or redirect to the mobile website if the app is not installed.
     **/
    fun openInstagramProfile() {
        if (isInstagramAppInstalled()) {
            val instagramIntent = Intent(Intent.ACTION_VIEW)
            val instagramUrl = getInstagramProfileURL()
            instagramIntent.data = Uri.parse(instagramUrl)
            context.startActivity(instagramIntent)
        } else {
            redirectToInstagramMobileWebsite()
        }
    }

    /**
     * Method to check if the Instagram app is installed on the user's device
     */
    private fun isInstagramAppInstalled(): Boolean {
        val packageManager: PackageManager = context.packageManager
        return try {
            packageManager.getPackageInfo("com.instagram.android", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Method to get the right URL for the profile
     */
    private fun getInstagramProfileURL(): String {
        val packageManager: PackageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.instagram.android", 0).longVersionCode
            if (versionCode >= 15520000) { // newer versions of Instagram app
                "https://www.instagram.com/_u/$INSTAGRAM_PAGE_ID"
            } else { // older versions of Instagram app
                "https://www.instagram.com/$INSTAGRAM_PAGE_ID"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            "https://www.instagram.com/$INSTAGRAM_PAGE_ID" // normal web URL
        }
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