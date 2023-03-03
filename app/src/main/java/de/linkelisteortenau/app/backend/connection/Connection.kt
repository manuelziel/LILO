package de.linkelisteortenau.app.backend.connection

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 *
 * Check the connection to the Server
 **/
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import de.linkelisteortenau.app.backend.debug.*
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class to check the connection
 *
 * @param context as Context
 **/
class Connection(val context: Context) {
    val debug: Boolean = Preferences(context).getSystemDebug()

    /**
     * Check Network Capabilities
     *
     * @return Boolean
     **/
    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                if (debug){
                    Log.d(DEBUG_CONNECTION, DEBUG_CONNECTION_CELLULAR_HAS_TRANSPORT)
                }
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                if (debug){
                    Log.d(DEBUG_CONNECTION, DEBUG_CONNECTION_WIFI_HAS_TRANSPORT)
                }
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                if (debug){
                    Log.d(DEBUG_CONNECTION, DEBUG_CONNECTION_ETHERNET_HAS_TRANSPORT)
                }
                return true
            } else {
                if (debug){
                    Log.d(DEBUG_CONNECTION, DEBUG_CONNECTION_HAS_NO_TRANSPORT)
                }
            }
        }
        return false
    }

    /**
     * Check connection to linke-liste-ortenau.de Server
     *
     * @return Boolean
     **/
    fun connectionToServer(): Boolean {
        return try {
            val command = "ping -c 1 linke-liste-ortenau.de"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: java.lang.Exception) {
            false
        }
    }
}