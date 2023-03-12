package de.linkelisteortenau.app.backend.preferences

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Preferences for app and user preferences
 **/
import android.content.Context
import androidx.preference.PreferenceManager
import de.linkelisteortenau.app.*

/**
 * Preferences for the app and user preferences
 *
 * @param context as Context
 **/
class Preferences(val context: Context) {
    private val prefManager = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Set the system debug to debug the APP
     *
     * @param debug is the Parameter of the user acceptation
     **/
    fun setSystemDebug(debug: Boolean) {
        prefManager.edit().putString(PREF_SYSTEM_DEBUG, debug.toString()).apply()
    }

    /**
     * Get the system debug to for debugging the APP
     * Represent the Parameter of the debugging acceptation
     *
     **/
    fun getSystemDebug(): Boolean {
        return prefManager.getString(PREF_SYSTEM_DEBUG, PREF_SYSTEM_DEBUG_VALUE).toString().toBoolean()
    }

    /**
     * Set the user privacy policy to use the APP
     *
     * @param accept is the Parameter of the user acceptation
     **/
    fun setUserPrivacyPolicy(accept: Boolean) {
        prefManager.edit().putString(PREF_USER_PRIVACY_POLICY, accept.toString()).apply()
    }

    /**
     * Get the user privacy policy to use the APP
     * Represent the Parameter of the user acceptation
     *
     * @return Boolean
     **/
    fun getUserPrivacyPolicy(): Boolean {
        return prefManager.getString(PREF_USER_PRIVACY_POLICY, PREF_USER_PRIVACY_POLICY_VALUE).toString().toBoolean()
    }

    /**
     * Set App Code Preference.
     * To check it if an Update are exist.
     *
     * @param code is the Value of the App Version
     **/
    fun setAppCode(code: Int) {
        prefManager.edit().putString(PREF_APP_UPDATE, code.toString()).apply()
    }

    /**
     * Get the last App Code to check if an Update exist.
     *
     * @return Integer
     **/
    fun getAppCode(): Int {
        return prefManager.getString(PREF_APP_UPDATE, PREF_APP_UPDATE_VALUE).toString().toInt()
    }

    /**
     * Set the user locale to use the APP
     *
     * @param locale is the Parameter of the user acceptation
     **/
    fun setLocale(locale: String) {
        prefManager.edit().putString(PREF_LOCALE, locale).apply()
    }

    /**
     * Get the user locale to use the APP
     * Represent the Parameter of the user locale
     *
     * @return String
     **/
    fun getLocale(): String {
        return prefManager.getString(PREF_LOCALE, PREF_LOCALE_VALUE).toString()
    }

    /**
     * Set the user Event Notification to show the push Notifications
     *
     * @param show is the Parameter of the user acceptation to show Notifications
     **/
    fun setUserShowEventsNotification(show: Boolean) {
        prefManager.edit().putString(PREF_USER_SHOW_EVENT_NOTIFICATION, show.toString()).apply()
    }

    /**
     * Get the user Event Notification to show the push Notifications
     * Represent the Parameter of the user acceptation to show Notifications
     *
     * @return Boolean
     **/
    fun getUserShowEventsNotification(): Boolean {
        return prefManager.getString(PREF_USER_SHOW_EVENT_NOTIFICATION, PREF_USER_SHOW_EVENT_NOTIFICATION_VALUE).toBoolean()
    }

    /**
     * Set the user Event Notification time multiplication to show the push Notifications within a certain time multiplication.
     *
     * @param multiplicator is the Parameter of the multiplication in Int
     **/
    fun setUserEventsNotificationTimeMultiplicator(multiplicator: Int) {
        prefManager.edit().putString(PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR, multiplicator.toString()).apply()
    }

    /**
     * Get the user Event Notification time multiplication to show the push Notifications within a certain time multiplication.
     *
     * @return Long
     **/
    fun getUserEventsNotificationTimeMultiplicator(): Int {
        return prefManager.getString(PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR, PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR_VALUE).toString().toInt()
    }

    /**
     * Get the user Article Notification to show the push Notification
     *
     * param
     */
    fun setUserShowArticlesNotification(show: Boolean) {
        prefManager.edit().putString(PREF_USER_SHOW_ARTICLE_NOTIFICATION, show.toString()).apply()
    }

    /**
     * Get the user Event Notification to show the push Notifications
     * Represent the Parameter of the user acceptation to show Notifications
     *
     * @return Boolean
     **/
    fun getUserShowArticlesNotification(): Boolean {
        return prefManager.getString(PREF_USER_SHOW_ARTICLE_NOTIFICATION, PREF_USER_SHOW_ARTICLE_NOTIFICATION_VALUE).toBoolean()
    }

    /**
     * Remove one preference. To remove you need the preference string
     * @example prefManager.edit().remove("pref_*").apply()
     *
     * @use Privacy Policy
     **/
    fun deleteAllPreferences() {
        prefManager.edit().clear().apply()
    }
}