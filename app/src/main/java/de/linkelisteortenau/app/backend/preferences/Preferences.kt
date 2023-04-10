package de.linkelisteortenau.app.backend.preferences

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Preferences for app and user preferences
 **/
import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import de.linkelisteortenau.app.*
import de.linkelisteortenau.app.backend.debug.DEBUG_PREFERENCES

/**
 * Preferences for the app and user preferences
 *
 * @param context as Context
 **/
class Preferences(
    val context: Context
) {
    private val prefManager = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Get the system debug to for debugging the APP
     * Represent the Parameter of the debugging acceptation
     **/
    fun getSystemDebug(): Boolean {
        var returnValue = PREF_SYSTEM_DEBUG_VALUE
        try {
            returnValue = prefManager.getBoolean(PREF_SYSTEM_DEBUG, returnValue)
        } catch (e: ClassCastException) {
            // Fallback if the value cannot be cast to an boolean
            Log.w(DEBUG_PREFERENCES, "The value System Debug cannot be cast. Fallback to default value: $returnValue")
            setSystemDebug(returnValue)
        }
        return returnValue
    }

    /**
     * Set the system debug to debug the APP
     *
     * @param debug is the Parameter of the user acceptation
     **/
    fun setSystemDebug(debug: Boolean) {
        try {
            prefManager.edit().putBoolean(PREF_SYSTEM_DEBUG, debug).apply()
        } catch (e: ClassCastException) {
            // Error if the value cannot be set to an boolean
            Log.w(DEBUG_PREFERENCES, "The value: $debug for System Debug cannot be set.")
        }
    }

    /**
     * Get the user privacy policy to use the APP
     * Represent the Parameter of the user acceptation
     *
     * @return Boolean
     **/
    fun getUserPrivacyPolicy(): Boolean {
        var returnValue = PREF_USER_PRIVACY_POLICY_VALUE
        try {
            returnValue = prefManager.getBoolean(PREF_USER_PRIVACY_POLICY, returnValue)
        } catch (e: ClassCastException) {
            // Fallback if the value cannot be cast to an Boolean
            Log.w(DEBUG_PREFERENCES, "The value User Privacy Policy cannot be cast. Fallback to default Boolean: $returnValue")
            setUserPrivacyPolicy(returnValue)
        }
        return returnValue
    }

    /**
     * Set the user privacy policy to use the APP
     *
     * @param accept is the Parameter of the user acceptation
     **/
    fun setUserPrivacyPolicy(accept: Boolean) {
        try {
            prefManager.edit().putBoolean(PREF_USER_PRIVACY_POLICY, accept).apply()
        } catch (e: ClassCastException) {
            // Error if the value cannot be set to an boolean
            Log.w(DEBUG_PREFERENCES, "The value: $accept for User Privacy Policy cannot be set.")
        }
    }

    /**
     * Get the last App Code to check if an Update exist.
     *
     * @return Integer
     **/
    fun getAppVersionCode(): Int {
        var returnValue = BuildConfig.VERSION_CODE - 1
        try {
            returnValue = prefManager.getInt(PREF_APP_UPDATE, PREF_APP_UPDATE_VALUE)
        } catch (e: ClassCastException) {
            // Fallback if the value cannot be cast to an integer
            Log.w(DEBUG_PREFERENCES, "The value App Version Code cannot be cast. Fallback to App Version Code: ${BuildConfig.VERSION_CODE - 1}")
        }
        return returnValue
    }

    /**
     * Set App Code Preference.
     * To check it if an Update are exist.
     *
     * @param versionCode is the Value of the App Version
     **/
    fun setAppVersionCode(versionCode: Int) {
        try {
            prefManager.edit().putInt(PREF_APP_UPDATE, versionCode).apply()
        } catch (e: ClassCastException) {
            // Error if the value cannot be set to an Integer
            Log.w(DEBUG_PREFERENCES, "The value: $versionCode for App Version Code cannot be set.")
        }
    }

    /**
     * Get the user locale to use the APP
     * Represent the Parameter of the user locale
     *
     * @return String
     **/
    fun getLocale(): String {
        var returnValue = PREF_LOCALE_VALUE
        try {
            returnValue = prefManager.getString(PREF_LOCALE, returnValue).toString()
        } catch (e: ClassCastException) {
            // Fallback if the value cannot be cast to an String
            Log.w(DEBUG_PREFERENCES, "The value Locale cannot be cast. Fallback to default String: $returnValue")
            setLocale(returnValue)
        }
        return returnValue
    }

    /**
     * Set the user locale to use the APP
     *
     * @param locale is the Parameter of the user acceptation
     **/
    fun setLocale(locale: String) {
        try {
            prefManager.edit().putString(PREF_LOCALE, locale).apply()
        } catch (e: ClassCastException) {
            // Error if the value cannot be set to an String
            Log.w(DEBUG_PREFERENCES, "The value: $locale for Locale cannot be set.")
        }
    }

    /**
     * Get the user Event Notification to show the push Notifications
     * Represent the Parameter of the user acceptation to show Notifications
     *
     * @return Boolean
     **/
    fun getUserShowEventsNotification(): Boolean {
        var returnValue = PREF_USER_SHOW_EVENT_NOTIFICATION_VALUE
        try {
            returnValue = prefManager.getBoolean(PREF_USER_SHOW_EVENT_NOTIFICATION, returnValue)
        } catch (e: ClassCastException) {
            // Fallback if the value cannot be cast to an boolean
            Log.w(DEBUG_PREFERENCES, "The value User Show Events Notification cannot be cast. Fallback to default value: $returnValue")
            setUserShowEventsNotification(returnValue)
        }
        return returnValue
    }

    /**
     * Set the user Event Notification to show the push Notifications
     *
     * @param show is the Parameter of the user acceptation to show event Notifications
     **/
    fun setUserShowEventsNotification(show: Boolean) {
        try {
            prefManager.edit().putBoolean(PREF_USER_SHOW_EVENT_NOTIFICATION, show).apply()
        } catch (e: ClassCastException) {
            // Error if the value cannot be set to an boolean
            Log.w(DEBUG_PREFERENCES, "The value: $show for User Show Events Notification cannot be set.")
        }
    }

    /**
     * Get the user Event Notification time multiplication to show the push Notifications within a certain time multiplication.
     *
     * @return Long
     **/
    fun getUserEventsNotificationTimeMultiplicator(): Int {
        var returnValue = PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR_VALUE
        try {
            returnValue = prefManager.getInt(PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR, returnValue)
        } catch (e: ClassCastException) {
            // Fallback if the value cannot be cast to an Integer
            Log.w(DEBUG_PREFERENCES, "The value User Events Notification Time Multiplicator cannot be cast. Fallback to default value: $returnValue")
            setUserEventsNotificationTimeMultiplicator(returnValue)
        }
        return returnValue
    }

    /**
     * Set the user Event Notification time multiplication to show the push Notifications within a certain time multiplication.
     *
     * @param multiplicator is the Parameter of the multiplication in Int
     **/
    fun setUserEventsNotificationTimeMultiplicator(multiplicator: Int) {
        try {
            prefManager.edit().putInt(PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR, multiplicator).apply()
        } catch (e: ClassCastException) {
            // Error if the value cannot be set to an Integer
            Log.w(DEBUG_PREFERENCES, "The value: $multiplicator for User Events Notification Time Multiplicator cannot be set.")
        }
    }

    /**
     * Get the user Articles Notification to show the push Notifications
     * Represent the Parameter of the user acceptation to show Notifications
     *
     * @return Boolean
     **/
    fun getUserShowArticlesNotification(): Boolean {
        var returnValue = PREF_USER_SHOW_ARTICLE_NOTIFICATION_VALUE
        try {
            returnValue = prefManager.getBoolean(PREF_USER_SHOW_ARTICLE_NOTIFICATION, returnValue)
        } catch (e: ClassCastException) {
            // Fallback if the value cannot be cast to an boolean
            Log.w(DEBUG_PREFERENCES, "The value User Show Articles Notification cannot be cast. Fallback to default value: $returnValue")
            setUserShowArticlesNotification(returnValue)
        }
        return returnValue
    }

    /**
     * Get the user Article Notification to show the push Notification
     *
     * @param show is the Parameter of the user acceptation to show article Notifications
     */
    fun setUserShowArticlesNotification(show: Boolean) {
        try {
            prefManager.edit().putBoolean(PREF_USER_SHOW_ARTICLE_NOTIFICATION, show).apply()
        } catch (e: ClassCastException) {
            // Error if the value cannot be set to an boolean
            Log.w(DEBUG_PREFERENCES, "The value: $show for User Show Articles Notification cannot be set.")
        }
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