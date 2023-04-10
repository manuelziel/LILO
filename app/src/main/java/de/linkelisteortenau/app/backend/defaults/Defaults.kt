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
    private val context: Context
) {
    private val preferences = Preferences(context)
    private val debug = preferences.getSystemDebug()

    /**
     * Set default values for preferences
     **/
    fun setDefaults(
    ) {
        preferences.setSystemDebug(false)
        preferences.setUserPrivacyPolicy(true)
        //preferences.setLocale()
        preferences.setUserShowEventsNotification(false)
        //preferences.setUserEventsNotificationTimeMultiplicator(1)
        preferences.setUserShowArticlesNotification(true)

        if (debug) {
            Log.d(DEBUG_SET_DEFAULTS, DEBUG_SET_DEFAULTS_DEFAULTS)
        }
    }

    /**
     * Update preferences with new defaults
     **/
    fun updatePreferences(
    ) {
        if (preferences.getAppVersionCode() != BuildConfig.VERSION_CODE) {
            // add new preferences here
            preferences.setAppVersionCode(BuildConfig.VERSION_CODE)

            if (debug) {
                Log.d(DEBUG_SET_DEFAULTS, DEBUG_SET_DEFAULTS_NEW_DEFAULTS)
            }
        }
    }
}