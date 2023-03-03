package de.linkelisteortenau.app.backend.permission

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Classes for Permission Notification
 **/
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import de.linkelisteortenau.app.backend.debug.DEBUG_PERMISSION_NOTIFICATION
import de.linkelisteortenau.app.backend.debug.DEBUG_PERMISSION_NOTIFICATION_FALSE
import de.linkelisteortenau.app.backend.debug.DEBUG_PERMISSION_NOTIFICATION_TRUE
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Main Class to get the Permission
 *
 *  @see <a href="https://developer.android.com/training/permissions/requesting#request-permission">Request Permission</a>
 **/
class PermissionNotification(activity: AppCompatActivity) {
    val debug = Preferences(activity.baseContext).getSystemDebug()

    /**
     * Register the permissions callback, which handles the user's response to the
     * system permissions dialog. Save the return value, an instance of
     * ActivityResultLauncher. You can use either a val, as shown in this snippet,
     * or a lateinit var in your onAttach() or onCreate() method.
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

                /**
                 * Function to check and request permission.
                 *
                 * Explain to the user that the feature is unavailable because the
                 * feature requires a permission that the user has denied. At the
                 * same time, respect the user's decision. Don't link to system
                 * settings in an effort to convince the user to change their
                 * decision.
                 */
                fun checkPermission(permission: String, requestCode: Int) {
                    if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
                        // Requesting the permission
                        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)

                        // Permission isn't granted. Continue the action or workflow in the app.
                        if (debug) {
                            Log.d(DEBUG_PERMISSION_NOTIFICATION, DEBUG_PERMISSION_NOTIFICATION_FALSE)
                        }

                    } else {

                        // Permission is granted. Continue the action or workflow in the app.
                        if (debug) {
                            Log.d(DEBUG_PERMISSION_NOTIFICATION, DEBUG_PERMISSION_NOTIFICATION_TRUE)
                        }
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkPermission(Manifest.permission.POST_NOTIFICATIONS, 101)
                } else {

                    // Permission isn't granted. Continue the action or workflow in the app.
                    if (debug) {
                        Log.d(DEBUG_PERMISSION_NOTIFICATION, DEBUG_PERMISSION_NOTIFICATION_FALSE)
                    }
                }
            }
        }
}