package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Link to Youtube Profile
 **/
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import de.linkelisteortenau.app.YOUTUBE_PAGE_ID

/**
 * Class for Youtube Profile
 *
 * @param context as Context
 **/
class Youtube(
    val context: Context
) {
    /**
     * Function to open the YouTube app with the channel link,
     * or redirect to the mobile website if the app is not installed.
     **/
    fun openYouTubeProfile() {
        if (isYouTubeAppInstalled()) {
            val youtubeIntent = Intent(Intent.ACTION_VIEW)
            val youtubeUrl = getYouTubeChannelURL()
            youtubeIntent.data = Uri.parse(youtubeUrl)
            context.startActivity(youtubeIntent)
        } else {
            redirectToYouTubeMobileWebsite()
        }
    }

    /**
     * Method to check if the YouTube app is installed on the user's device
     */
    private fun isYouTubeAppInstalled(): Boolean {
        val packageManager: PackageManager = context.packageManager
        return try {
            packageManager.getPackageInfo("com.google.android.youtube", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Method to get the right URL for the YouTube channel
     */
    private fun getYouTubeChannelURL(): String {
        val packageManager: PackageManager = context.packageManager
        return try {
            val versionCode = packageManager.getPackageInfo("com.google.android.youtube", 0).longVersionCode
            if (versionCode >= 14700) { // newer versions of YouTube app
                "https://www.m.youtube.com/$YOUTUBE_PAGE_ID"
            } else { // older versions of YouTube app
                "https://www.m.youtube.com/$YOUTUBE_PAGE_ID"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            "https://www.m.youtube.com/$YOUTUBE_PAGE_ID" // normal web URL
        }
    }

    /**
     * Method to redirect to the mobile website if the YouTube app is not installed
     */
    private fun redirectToYouTubeMobileWebsite() {
        val youtubeUrl = "https://www.m.youtube.com/$YOUTUBE_PAGE_ID"
        val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        context.startActivity(websiteIntent)
    }
}