package de.linkelisteortenau.app.backend

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 *
 * Run this Service in Background to check User-Rights, check and get Events, show Push-Notifications and
 * check if the user interested in other stuff etc.
 **/
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import de.linkelisteortenau.app.*
import de.linkelisteortenau.app.backend.articles.Articles
import de.linkelisteortenau.app.backend.connection.Connection
import de.linkelisteortenau.app.backend.events.Events
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.debug.*
import de.linkelisteortenau.app.backend.notification.BackgroundNotification

/**
 * Class for the Background Service of the APP LiLO.
 * Intend from the MainActivity as get Service.
 **/
open class BackgroundService : Service() {

    // Handler
    private val handler = Handler(Looper.getMainLooper())

    /**
     * Lifecycle.
     *
     * Activity lifecycle create
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun onCreate() {
        super.onCreate()
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_ON_CREATE)
        }
    }

    /**
     * Lifecycle.
     *
     * Activity lifecycle start command
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_ON_START_COMMAND)
        }
        runBackgroundHandler()
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * Lifecycle.
     *
     * Activity lifecycle start Service
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun startService(
        service: Intent?
    ): ComponentName? {
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_START_SERVICE)
        }
        return super.startService(service)
    }

    /**
     * Lifecycle.
     *
     * Activity lifecycle start foreground Service
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun startForegroundService(
        service: Intent?
    ): ComponentName? {
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_FOREGROUND_SERVICE)
        }
        return super.startForegroundService(service)
    }

    /**
     * Lifecycle.
     *
     * Activity lifecycle bind
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun onBind(
        intent: Intent?
    ): IBinder? {
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_BIND)
        }
        return null // "Not yet implemented"
    }

    /**
     * Lifecycle.
     *
     * Activity lifecycle unbind
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun onUnbind(
        intent: Intent?
    ): Boolean {
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_UNBIND)
        }
        return super.onUnbind(intent)
    }

    /**
     * Lifecycle.
     *
     * Activity lifecycle rebind
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun onRebind(
        intent: Intent?
    ) {
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_REBIND)
        }
        super.onRebind(intent)
    }

    /**
     * Lifecycle.
     *
     * Activity lifecycle destroy
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/components/services">Services</a>
     * @see <a href="https://guides.codepath.com/android/starting-background-services">Start Service in Background</a>
     **/
    override fun onDestroy() {
        if (Preferences(this).getSystemDebug()) {
            Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_DESTROY)
        }
        super.onDestroy()
    }

    /**
     *  Start Event Handler Service.
     *  1. check the accepted user privacy policy
     *
     *  2. check the current Events in the SQL Database
     *  and delete double Events or Events there are in the past.
     *
     *  3. check the connection to the server if the connection are available
     *  load the Events from server again and check for new Events.
     *
     *  4. check for new Push-Notifications and show them.
     **/
    private var debug: Boolean = false
    private fun runBackgroundHandler() {
        // Check accepted user privacy policy
        debug = Preferences(this).getSystemDebug()
        val userPrivacyPolicy = Preferences(this).getUserPrivacyPolicy()

        if (debug) {
            Log.d(DEBUG_BACKGROUND_SERVICE, "Debug is: $debug")
            Log.d(DEBUG_BACKGROUND_SERVICE, "User has privacy policy accepted: $userPrivacyPolicy")
        }

        // Check the connection to the server and the accepted user privacy policy.
        // Check Events and Notifications from Server.
        eventsLoop(this)
        articlesLoop(this)
        notificationsLoop(this)
    }

    /**
     * Check Events in an time Loop.
     *
     * @param context as Context
     **/
    private fun eventsLoop(
        context: Context
    ){
        // Run Check and get all Events from Server
        handler.postDelayed(object : Runnable {
            val debug = Preferences(context).getSystemDebug()
            val connectionToServer = Connection(context).connectionToServer()
            val userPrivacyPolicy = Preferences(context).getUserPrivacyPolicy()

            override fun run() {
                if (debug){
                    Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_BACKGROUND_LOOP_EVENTS)
                }

                // Check for Server connection and privacy policy and check the Events.
                // Than get all Events if connection is available.
                if (connectionToServer && userPrivacyPolicy) {
                    Events(context).check()
                    Events(context).loadEventsFromServer()
                } else {
                    Events(context).check()
                }

                //handler.postDelayed(this, (60000))
                handler.postDelayed(this, (NOTIFICATION_CHECK_SERVER_EVENT_DELAY.toLong() * NOTIFICATION_CHECK_SERVER_EVENT_DELAY_MULTIPLICATOR))
            }
        }, 0)
    }

    /**
     * Check Articles in an time Loop
     *
     * @param context as Context
     **/
    private fun articlesLoop(
        context: Context
    ){
        // Run Check and get Articles from Server
        handler.postDelayed(object : Runnable {
            val debug = Preferences(context).getSystemDebug()
            val connectionToServer = Connection(context).connectionToServer()
            val userPrivacyPolicy = Preferences(context).getUserPrivacyPolicy()

            override fun run() {
                if (debug){
                    Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_BACKGROUND_LOOP_ARTICLES)
                }

                // Check for Server connection and privacy policy and check the Events.
                // Than get all Events if connection is available.
                if (connectionToServer && userPrivacyPolicy) {
                    Articles(context).check()
                    Articles(context).loadArticlesFromServer()
                } else {
                    Articles(context).check()
                }

                //handler.postDelayed(this, (60000))
                handler.postDelayed(this, (NOTIFICATION_CHECK_SERVER_ARTICLE_DELAY.toLong() * NOTIFICATION_CHECK_SERVER_ARTICLE_DELAY_MULTIPLICATOR))
            }
        }, 0)
    }

    /**
     * Check for new Notifications in an time Loop.
     *
     * @param context as Context
     **/
    private fun notificationsLoop(
        context: Context
    ){
        // Check new Notifications and show them.
        BackgroundNotification(this).createNotificationChannel()
        handler.postDelayed(object : Runnable {
            val debug = Preferences(context).getSystemDebug()
            val connectionToServer = Connection(context).connectionToServer()
            val userPrivacyPolicy = Preferences(context).getUserPrivacyPolicy()

            override fun run() {
                if (debug){
                    Log.d(DEBUG_BACKGROUND_SERVICE, DEBUG_BACKGROUND_LOOP_NOTIFICATION)
                }

                // Check for Server connection and privacy policy.
                // Than create Push Notification.
                if (connectionToServer && userPrivacyPolicy) {
                    BackgroundNotification(context).newNotifications()
                }

                //handler.postDelayed(this, (60000))
                handler.postDelayed(this, (NOTIFICATION_CHECK_NOTIFICATION_DELAY.toLong() * NOTIFICATION_CHECK_NOTIFICATION_DELAY_MULTIPLICATOR))
            }
        }, 1000)
    }
}