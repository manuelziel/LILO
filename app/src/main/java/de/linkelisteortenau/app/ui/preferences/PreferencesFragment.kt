package de.linkelisteortenau.app.ui.preferences

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * View and set the user preferences for the hole app
 **/
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import de.linkelisteortenau.app.*
import de.linkelisteortenau.app.backend.notification.BackgroundNotification
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.ui.dialog.DialogLicence
import de.linkelisteortenau.app.ui.dialog.DialogReset


/**
 * Class Preferences as Fragment
 **/
class PreferencesFragment : Fragment() {

    /**
     * Lifecycle
     *
     * Fragment lifecycle create
     * @see <a href="https://developer.android.com/guide/fragments/lifecycle">Fragment Lifecycle</a>
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.pref_user_title)

        // Set switch Event Notification with preference Value
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .edit().putBoolean(
                USER_PREF_KEY_EVENT_NOTIFICATION, Preferences(requireContext()).getUserShowEventsNotification()
            ).apply()

        // Set switch Article Notification with preference Value
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .edit().putBoolean(
                USER_PREF_KEY_ARTICLE_NOTIFICATION,
                Preferences(requireContext()).getUserShowArticlesNotification()
            ).apply()

        // Set switch Debug with preference Value
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .edit().putBoolean(
                USER_PREF_KEY_DEBUG,
                Preferences(requireContext()).getSystemDebug()
            ).apply()

        // Replace FragmentLayout with Preferences XML
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, UserPreferenceFragment())
            .commit()
    }

    /**
     * Load Preferences
     *
     * Preference Fragment
     * @see <a href="https://developer.android.com/develop/ui/views/components/settings">Settings Preference</a>
     **/
    class UserPreferenceFragment : PreferenceFragmentCompat() {

        /** ATTACH
        override fun onAttach(co
        ntext: Context) {
            super.onAttach(context)

            // Callback to last fragment
            val callback: OnBackPressedCallback =
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        // Replace FragmentLayout with Preferences XML
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_view, SetPrefRootFragment())
                            .commit()
                    }
                }
            requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        }**/

        private var cntButtonAppVersion         = 0
        private var boolButtonBeta              = false
        private var boolButtonDeveloper         = false
        @SuppressLint("IntentReset")
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.pref_user_preferences, rootKey)

            val prefButtonReset                 : Preference = preferenceManager.findPreference(USER_PREF_KEY_RESET)!!
            val prefButtonAppVersion            : Preference = preferenceManager.findPreference(USER_PREF_KEY_APP_VERSION)!!
            val prefButtonFeedback              : Preference = preferenceManager.findPreference(USER_PREF_KEY_FEEDBACK)!!
            val prefButtonThirdPartySoftware    : Preference = preferenceManager.findPreference(USER_PREF_KEY_THIRD_PARTY_SOFTWARE)!!
            val prefButtonLicence               : Preference = preferenceManager.findPreference(USER_PREF_KEY_LICENCE)!!
            val prefButtonBeta                  : Preference = preferenceManager.findPreference(USER_PREF_KEY_BETA)!!
            val prefButtonEventNotification     : Preference = preferenceManager.findPreference(USER_PREF_KEY_EVENT_NOTIFICATION)!!
            val prefButtonArticleNotification   : Preference = preferenceManager.findPreference(USER_PREF_KEY_ARTICLE_NOTIFICATION)!!
            val prefButtonDeveloper             : Preference = preferenceManager.findPreference(USER_PREF_KEY_DEVELOPER)!!
            val prefButtonDebug                 : Preference = preferenceManager.findPreference(USER_PREF_KEY_DEBUG)!!

            // About Version
            val verNumber   = BuildConfig.VERSION_CODE.toString()
            val ver         = "${getString(R.string.global_app_code)}: " + verNumber + "  " + "${getString(R.string.global_app_version)}: " + BuildConfig.VERSION_NAME
            prefButtonAppVersion.summary = ver
            prefButtonEventNotification.isVisible = false
            prefButtonEventNotification.isEnabled = false
            prefButtonArticleNotification.isVisible = false
            prefButtonArticleNotification.isEnabled = false
            prefButtonDeveloper.isVisible = false
            prefButtonDeveloper.isEnabled = false
            prefButtonDebug.isVisible = false
            prefButtonDebug.isEnabled = false

            // Reset App
            prefButtonReset.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                // Start Dialog
                try {
                    DialogReset().show(childFragmentManager, DIALOG_TAG)
                } finally {
                    //
                }
                true
            }

            // App Version
            prefButtonAppVersion.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                try { //
                    ++cntButtonAppVersion
                    if ( cntButtonAppVersion == 10 ) {
                        prefButtonDeveloper.isVisible = true
                        prefButtonDeveloper.isEnabled = true
                    }
                } finally {
                    //
                }
                true
            }

            // App Feedback
            prefButtonFeedback.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                try { //
                    val emailAddress    = getString(R.string.feedback_mail_address)
                    val emailSubject    = getString(R.string.feedback_mail_subject)
                    val verString       = "${getString(R.string.global_app_code)}: " + BuildConfig.VERSION_CODE + "  " + "${getString(R.string.global_app_version)}: " + BuildConfig.VERSION_NAME
                    val phone           = Build.MANUFACTURER + " " + Build.MODEL
                    val emailTxt        =
                            verString + " " +
                            getString(R.string.feedback_mail_phone) + ": " + phone + "\n\n" +
                            getString(R.string.contact_mail_text)

                    val intentEmail = Intent(Intent.ACTION_SEND, Uri.fromParts("mailto", emailAddress, null))
                    //Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null))
                    intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    intentEmail.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                    intentEmail.putExtra(Intent.EXTRA_TEXT, emailTxt)
                    intentEmail.type = "message/rfc822"
                    startActivity(intentEmail)
                } finally {
                    //
                }
                true
            }

            // Third-party Software
            prefButtonThirdPartySoftware.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                // Start Dialog
                try {
                    OssLicensesMenuActivity.setActivityTitle(getString(R.string.pref_user_third_party_software_title))
                    startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                } finally {
                    //
                }
                true
            }

            // LICENCE
            prefButtonLicence.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                // Start Dialog
                try {
                    DialogLicence().show(childFragmentManager, DIALOG_TAG)
                } finally {
                    //
                }
                true
            }

            // Beta
            prefButtonBeta.onPreferenceClickListener = Preference.OnPreferenceClickListener{
                // Show Beta Content
                try {
                    if (boolButtonBeta) {
                        prefButtonEventNotification.isVisible = false
                        prefButtonEventNotification.isEnabled = false

                        prefButtonArticleNotification.isVisible = false
                        prefButtonArticleNotification.isEnabled = false
                        boolButtonBeta = false
                    } else {
                        prefButtonEventNotification.isVisible = true
                        prefButtonEventNotification.isEnabled = true

                        prefButtonArticleNotification.isVisible = true
                        prefButtonArticleNotification.isEnabled = true
                        boolButtonBeta = true
                    }
                } finally {
                    //
                }
                true
            }

            // Event Notification
            prefButtonEventNotification.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{ _, newValue ->
                // Change Boolean at Event Notification
                try {
                    context?.let { Preferences(it).setUserShowEventsNotification(newValue.toString().toBoolean()) }
                    if(newValue == true){
                        context?.let { BackgroundNotification(it).newNotifications() }
                    }
                } finally {
                    //
                }
                true
            }

            // Article Notification
            prefButtonArticleNotification.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{ _, newValue ->
                // Change Boolean at Article Notification
                try {
                    context?.let { Preferences(it).setUserShowArticlesNotification(newValue.toString().toBoolean()) }
                    if(newValue == true){
                        context?.let { BackgroundNotification(it).newNotifications() }
                    }
                } finally {
                    //
                }
                true
            }

            // Developer
            prefButtonDeveloper.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                //
                try {
                    if (boolButtonDeveloper) {
                        prefButtonDebug.isVisible = false
                        prefButtonDebug.isEnabled = false
                        boolButtonDeveloper = false
                    } else {
                        prefButtonDebug.isVisible = true
                        prefButtonDebug.isEnabled = true
                        boolButtonDeveloper = true
                    }
                } finally {
                    //
                }
                true
            }

            // Debug
            prefButtonDebug.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{ _, newValue ->
                try { //
                    context?.let { Preferences(it).setSystemDebug(newValue.toString().toBoolean()) }
                } finally {
                    //
                }
                true
            }
        }
    }
}