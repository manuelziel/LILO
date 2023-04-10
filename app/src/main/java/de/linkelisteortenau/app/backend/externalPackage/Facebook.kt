package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Invitation link to Facebook Group
 **/
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import de.linkelisteortenau.app.*

/**
 * Class for Facebook Group
 *
 * @param context as Context
 **/
class Facebook(
    val context: Context
    ) {

    /**
     * Function to open the Facebook app with the group link to the Group,
     * or redirect to the mobile website if the app is not installed.
     **/
    fun performToGroup() {
        if (isFacebookAppInstalled()) {
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            val facebookUrl = getFacebookPageURL()
            facebookIntent.data = Uri.parse(facebookUrl)
            context.startActivity(facebookIntent)
        } else {
            redirectToFacebookMobileWebsite()
        }
    }

    /**
     * Method to check if the Facebook app is installed on the user's device
     */
    private fun isFacebookAppInstalled(): Boolean {
        val packageManager: PackageManager = context.packageManager
        return try {
            packageManager.getPackageInfo("com.facebook.katana", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Method to get the right URL to use in the intent
     */
    private fun getFacebookPageURL(): String {
        val packageManager: PackageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).longVersionCode
            if (versionCode >= 3002850) { //newer versions of FB app
                "fb://facewebmodal/f?href=$FACEBOOK_GROUP_URL"
            } else { //older versions of FB app
                "fb://page/$FACEBOOK_PAGE_ID"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            FACEBOOK_GROUP_URL //normal web url
        }
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