package de.linkelisteortenau.app

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 **/
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import de.linkelisteortenau.app.backend.BackgroundService
import de.linkelisteortenau.app.backend.notification.EnumNotificationBundle
import de.linkelisteortenau.app.backend.notification.EnumNotificationTyp
import de.linkelisteortenau.app.backend.permission.PermissionNotification
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.databinding.ActivityMainBinding
import de.linkelisteortenau.app.ui.preferences.PrivacyPolicy

/**
 * Main Class for the APP
 **/
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    /**
     * Lifecycle
     *
     * Activity lifecycle create
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     **/
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        // Binding View Content
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        //Draw Navigation Controller
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Start background Service
        startBackgroundService()

        // Intent to privacy policy activity if no Privacy Policy is set.
        userPrivacyPolicy()

        // Request user for Notification permissions
        userPermissionNotification()

        // TODO DynamicColors and fix ThemeStyles

        // Activity has started from Fragment with extras to load other Fragment with extras
        intent.extras?.let { intentFromPushNotification(it, navController) }

        /**
         * Binding Snackbar as Action
         **/
        /*
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        /**
         * Global Nav Controller
         **/
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_event,
                R.id.nav_health_care,
                R.id.nav_local_transport,
                R.id.nav_housing,
                R.id.nav_social,
                R.id.nav_training,
                R.id.nav_deep_geothermal_energy
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    /**
     * Lifecycle
     *
     * Activity lifecycle destroy
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     **/
    override fun onDestroy() {
        super.onDestroy()
        this.finishAffinity()
    }

    /**
     * Options Menu
     **/
    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    /**
     * Navigate Up
     *
     * Show Navigation Controller Content when press Burger
     **/
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Start background Service
     **/
    private fun startBackgroundService() {
        if (Preferences(this).getUserPrivacyPolicy()) {
            val intent = Intent(this, BackgroundService::class.java)

            val pIntent = PendingIntent.getService(
                this, 1, intent,
                PendingIntent.FLAG_MUTABLE
            )
            pIntent.send()
        }
    }

    /**
     * Intent to privacy policy activity if no Privacy Policy is set.
     * User must set the Privacy Policy to use the App.
     **/
    private fun userPrivacyPolicy() {
        if (!Preferences(this).getUserPrivacyPolicy()) {

            val intent = Intent(this, PrivacyPolicy::class.java)

            val pIntent = PendingIntent.getActivity(
                this, 1, intent,
                PendingIntent.FLAG_MUTABLE,
            )
            pIntent.send()
        }
    }

    /**
     * Request permission Launcher
     *
     * Function to request the user for Notifications
     **/
    private fun userPermissionNotification() {
        PermissionNotification(this).requestPermissionLauncher.launch("Permission")
    }

    /**
     * Bundle from intent Push-Notification
     *
     * Function to load Fragment with extras
     **/
    private fun intentFromPushNotification(
        bundle: Bundle,
        navController: NavController
    ) {
        val extBundle: Bundle = bundle
        val typ: String?
        val link: String?

        // Get Extras
        try {
            typ = extBundle.getString(EnumNotificationBundle.TYP.string).toString()
            link = extBundle.getString(EnumNotificationBundle.LINK.string).toString()
        } finally {
        }

        // Event
        if ((typ == EnumNotificationTyp.EVENT.toString()) && (!link.isNullOrBlank())) {
            val extra = Bundle()
            extra.putString(EnumNotificationBundle.LINK.string, link)
            navController.navigate(R.id.nav_event_content, extra)
        }

        // Article
        if ((typ == EnumNotificationTyp.ARTICLE.toString()) && (!link.isNullOrBlank())) {
            val extra = Bundle()
            extra.putString(EnumNotificationBundle.LINK.string, link)
            navController.navigate(R.id.nav_home, extra)
        }
    }
}