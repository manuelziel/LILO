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
import de.linkelisteortenau.app.backend.debug.DEBUG_BACKGROUND_BROADCAST_RECEIVER
import de.linkelisteortenau.app.backend.debug.DEBUG_INTENT_SERVICE
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class for the Broadcast Receiver
 **/
class BackgroundBroadcastReceiver : BroadcastReceiver() {

    /**
     * Receive the backend android.intent.action.BOOT_COMPLETED filter
     * and start background Service
     **/
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {
        // Check accepted user privacy policy
        val debug = Preferences(context).getSystemDebug()

        if (debug){
            Log.d(DEBUG_BACKGROUND_BROADCAST_RECEIVER, DEBUG_INTENT_SERVICE)
        }

        val myIntent = Intent(context, BackgroundService::class.java)
        context.startService(myIntent)
    }
}