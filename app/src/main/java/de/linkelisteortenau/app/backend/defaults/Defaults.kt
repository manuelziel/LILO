package de.linkelisteortenau.app.backend.defaults

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * Set Defaults for the App
 **/
import android.content.Context
import android.util.Log
import de.linkelisteortenau.app.BuildConfig
import de.linkelisteortenau.app.backend.debug.DEBUG_SET_DEFAULTS
import de.linkelisteortenau.app.backend.debug.DEBUG_SET_DEFAULTS_DEFAULTS
import de.linkelisteortenau.app.backend.debug.DEBUG_SET_DEFAULTS_NEW_DEFAULTS
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class to set all Defaults for the App
 *
 * @param context as Context
 **/
class Defaults(
    val context: Context
) {
    val debug = Preferences(context).getSystemDebug()

    /**
     * Set Defaults for Preferences
     **/
    fun setDefaults(
    ) {
        Preferences(context).setSystemDebug(false)
        Preferences(context).setUserPrivacyPolicy(true)
        //Preferences(context).setLocale()
        Preferences(context).setUserShowEventsNotification(false)
        //Preferences(context).setUserEventsNotificationTimeMultiplicator(1)
        Preferences(context).setUserShowArticlesNotification(true)

        if (debug) {
            Log.d(DEBUG_SET_DEFAULTS, DEBUG_SET_DEFAULTS_DEFAULTS)
        }
    }

    /**
     * Set Defaults for new Preferences if updated. Or reset an old Preference.
     **/
    fun setNewPreferences(
    ) {
        if (Preferences(context).getAppCode() != BuildConfig.VERSION_CODE) {
            // Add new preference Settings here

            Preferences(context).setAppCode(BuildConfig.VERSION_CODE)

            if (debug) {
                Log.d(DEBUG_SET_DEFAULTS, DEBUG_SET_DEFAULTS_NEW_DEFAULTS)
            }
        }
    }
}