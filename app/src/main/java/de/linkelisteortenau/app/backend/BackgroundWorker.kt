package de.linkelisteortenau.app.backend

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 *
 * Run this Service in Background to check User-Rights, check and get Events, show Push-Notifications and
 * check if the user interested in other stuff etc.
 **/
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import de.linkelisteortenau.app.backend.articles.Articles
import de.linkelisteortenau.app.backend.connection.Connection
import de.linkelisteortenau.app.backend.debug.DEBUG_BACKGROUND_LOOP_ARTICLES
import de.linkelisteortenau.app.backend.debug.DEBUG_BACKGROUND_LOOP_EVENTS
import de.linkelisteortenau.app.backend.debug.DEBUG_BACKGROUND_WORKER
import de.linkelisteortenau.app.backend.events.Events
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class for the Background Worker of the APP LiLO.
 * From the MainActivity or Broadcast Receiver.
 *
 * @see <a href="https://developer.android.com/guide/background/persistent/getting-started">WorkManager</a>
 * @param context as Context
 * @param workerParams with Parameter for the Worker
 * @result Worker with Context and Parameter for the Worker
 **/
class BackgroundWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {

        // Do the work here
        runBackgroundHandler()

        return Result.retry()
    }

    /**
     *  Start Event Handler Worker.
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
        debug = Preferences(context).getSystemDebug()
        val userPrivacyPolicy = Preferences(context).getUserPrivacyPolicy()

        if (debug) {
            Log.d(DEBUG_BACKGROUND_WORKER, "Debug is: $debug")
            Log.d(DEBUG_BACKGROUND_WORKER, "User has privacy policy accepted: $userPrivacyPolicy")
        }

        // Check the connection to the server and the accepted user privacy policy.
        // Check Events and Notifications from Server.
        eventsLoop(context)
        articlesLoop(context)
    }

    /**
     * Check Events in an time Loop.
     *
     * @param context as Context
     **/
    private fun eventsLoop(
        context: Context
    ) {
        // Run Check and get all Events from Server
        val debug = Preferences(context).getSystemDebug()
        val connectionToServer = Connection(context).connectionToServer()
        val userPrivacyPolicy = Preferences(context).getUserPrivacyPolicy()

        if (debug) {
            Log.d(DEBUG_BACKGROUND_WORKER, DEBUG_BACKGROUND_LOOP_EVENTS)
        }

        // Check for Server connection and privacy policy and check the Events.
        // Than get all Events if connection is available.
        if (connectionToServer && userPrivacyPolicy) {
            Events(context).check()
            Events(context).loadEventsFromServer()
        } else {
            Events(context).check()
        }
    }

    /**
     * Check Articles in an time Loop
     *
     * @param context as Context
     **/
    private fun articlesLoop(
        context: Context
    ) {
        // Run Check and get Articles from Server
        val debug = Preferences(context).getSystemDebug()
        val connectionToServer = Connection(context).connectionToServer()
        val userPrivacyPolicy = Preferences(context).getUserPrivacyPolicy()

        if (debug) {
            Log.d(DEBUG_BACKGROUND_WORKER, DEBUG_BACKGROUND_LOOP_ARTICLES)
        }

        // Check for Server connection and privacy policy and check the Events.
        // Than get all Events if connection is available.
        if (connectionToServer && userPrivacyPolicy) {
            Articles(context).check()
            Articles(context).loadArticlesFromServer()
        } else {
            Articles(context).check()
        }
    }
}


