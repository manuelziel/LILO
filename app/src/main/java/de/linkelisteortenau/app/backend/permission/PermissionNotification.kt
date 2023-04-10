package de.linkelisteortenau.app.backend.permission

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Classes for Permission Notification
 **/
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import de.linkelisteortenau.app.backend.debug.DEBUG_PERMISSION_NOTIFICATION
import de.linkelisteortenau.app.backend.debug.DEBUG_PERMISSION_NOTIFICATION_TRUE
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class to request permission for notifications
 *  @see <a href="https://developer.android.com/training/permissions/requesting#request-permission">Request Permission</a>
 **/
class PermissionNotification(
    val activity: AppCompatActivity
) {
    private val debug = Preferences(activity.baseContext).getSystemDebug()

    /**
     * Callback to handle user's response to the system permissions dialog.
     */
    val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in the app.
            if (debug) {
                Log.d(DEBUG_PERMISSION_NOTIFICATION, DEBUG_PERMISSION_NOTIFICATION_TRUE)
            }
        } else {
            // Permission isn't granted. Request it.
            requestPermission()
        }
    }

    /**
     * Requests the notification permission.
     */
    private fun requestPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val requestCode = 101
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        } else {
            // Permission is already granted.
            if (debug) {
                Log.d(DEBUG_PERMISSION_NOTIFICATION, DEBUG_PERMISSION_NOTIFICATION_TRUE)
            }
        }
    }
}