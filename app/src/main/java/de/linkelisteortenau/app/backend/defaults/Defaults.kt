package de.linkelisteortenau.app.backend.defaults

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * Set defaults for the App
 **/
import android.content.Context
import de.linkelisteortenau.app.PREF_SYSTEM_DEBUG_VALUE
import de.linkelisteortenau.app.PREF_USER_SHOW_EVENT_NOTIFICATION
import de.linkelisteortenau.app.PREF_USER_SHOW_EVENT_NOTIFICATION_VALUE
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class to set all defaults for the App
 *
 * @param context as Context
 **/
class Defaults (val context: Context){

    /**
     * Set Defaults for Preferences
     **/
    fun setDefaults(
    ){
        Preferences(context).setSystemDebug(false)
        Preferences(context).setUserPrivacyPolicy(true)
        //Preferences(context).setLocale()
        Preferences(context).setUserShowEventsNotification(false)
        //Preferences(context).setUserEventsNotificationTimeMultiplicator(1)
        Preferences(context).setUserShowArticlesNotification(true)
    }
}