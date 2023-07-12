package de.linkelisteortenau.app.backend.externalPackage

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Link to Youtube Profile
 **/
import android.content.Context
import android.content.Intent
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
        redirectToYouTubeMobileWebsite()
    }

    /**
     * Method to redirect to the mobile website if the YouTube app is not installed
     */
    private fun redirectToYouTubeMobileWebsite() {
        val youtubeUrl = "https://www.youtube.com/$YOUTUBE_PAGE_ID"
        val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        context.startActivity(websiteIntent)
    }
}