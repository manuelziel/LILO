package de.linkelisteortenau.app.backend

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Classes for the Broadcast Receiver
 **/
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.*
import de.linkelisteortenau.app.NOTIFICATION_WORKER_LOOP_TIME_MIN
import de.linkelisteortenau.app.backend.debug.DEBUG_BACKGROUND_BROADCAST_RECEIVER
import de.linkelisteortenau.app.backend.debug.DEBUG_INTENT_WORKER
import de.linkelisteortenau.app.backend.preferences.Preferences
import java.util.concurrent.TimeUnit

/**
 * Class for the Broadcast Receiver
 **/
class BackgroundBroadcastReceiver : BroadcastReceiver() {

    /**
     * Receive the backend android.intent.action.BOOT_COMPLETED filter
     * and start background Worker.
     **/
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {
        // Check accepted user privacy policy
        val debug = Preferences(context).getSystemDebug()

        if (debug){
            Log.d(DEBUG_BACKGROUND_BROADCAST_RECEIVER, DEBUG_INTENT_WORKER)
        }

        if (Preferences(context).getUserPrivacyPolicy()) {
            workRequest(context)
        }
    }

    /**
     * Start background Worker
     * @see <a href="https://developer.android.com/guide/background/persistent/getting-started">WorkManager</a>
     * @see <a href="https://developer.android.com/guide/background/persistent/getting-started/define-work#retries_backoff">Define Work</a>
     **/
    fun workRequest(
        context: Context
    ){
        val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<BackgroundWorker>( NOTIFICATION_WORKER_LOOP_TIME_MIN.toLong(), TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "LILO",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}