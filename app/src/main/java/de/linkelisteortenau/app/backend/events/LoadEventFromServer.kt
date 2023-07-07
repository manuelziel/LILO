package de.linkelisteortenau.app.backend.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Classes for the Events to load, save, check and delete.
 **/
import android.content.Context
import android.util.Log
import de.linkelisteortenau.app.TIME_FORMAT_GLOBAL
import de.linkelisteortenau.app.TIME_MINUTE
import de.linkelisteortenau.app.WEB_VIEW_URL_EVENTS
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_LOAD_TEST_LINK
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_LOAD_TEST_TITLE
import de.linkelisteortenau.app.backend.debug.DEBUG_LOAD_EVENT
import de.linkelisteortenau.app.backend.notification.BackgroundNotification
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.time.Time
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.EnumMap

/**
 * Class to load Events from the Web Server
 **/
class LoadEventFromServer(
    val context: Context
    ) {
    val debug = Preferences(context).getSystemDebug()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    /**
     * Load all Events in an Kotlin Coroutine Scope and Supervisor Job.
     *
     * Launched in the main thread, Every coroutine builder (like launch, async, and others)
     * and every scoping function (like coroutineScope and withContext) provides its own
     * scope with its own Job instance into the inner block of code it runs.
     * By convention, they all wait for all the coroutines inside their block to complete
     * before completing themselves, thus enforcing the structured concurrency.
     * See Job documentation for more details.
     *
     * Creates a supervisor job object in an active state.
     *
     *
     * @see <a href="https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html">SupervisorJob</a>
     * @see <a href="https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/">CoroutineScope</a>
     * **/
    fun loadEvents() {
        scope.launch {
            loadJSONFunction()
        }
    }

    /**
     * Load all Events with JSON-LD from https://www.Linke-Liste-Ortenau.de/termine/
     * Session load timeout are disabled!
     *
     * @see <a href="https://www.linke-liste-ortenau.de/termine/">Events Page</a>
     **/
    private val eventContent = "null" // No Content implemented
    private fun loadJSONFunction() {
        try {
            val doc: Document = Jsoup.connect(WEB_VIEW_URL_EVENTS).timeout(0).get()
            var cnt = 0

            // In this case you want the first script tag
            val e = doc.select("script[type=application/ld+json]")

            val array = JSONArray(e.html())
            val lenght = array.length() - 1

            for (i in 0..lenght) {
                cnt++
                val mutableMap: MutableMap<EnumEvent, String> = EnumMap(EnumEvent::class.java)
                val obj = array.getJSONObject(i)

                mutableMap[EnumEvent.TITLE]     = obj.get("name").toString()
                mutableMap[EnumEvent.START]     = obj.get("startDate").toString()
                mutableMap[EnumEvent.END]       = obj.get("endDate").toString()
                mutableMap[EnumEvent.LINK]      = obj.get("url").toString()
                mutableMap[EnumEvent.CONTENT]   = eventContent
                mutableMap[EnumEvent.FLAG]      = false.toString()
                mutableMap[EnumEvent.PATTER]    = TIME_FORMAT_GLOBAL

                // Set all Event Flags and Save Event
                Events(context).setAllEventFlags(mutableMap) // Set all DB Event flags to false
                Events(context).saveEvent(mutableMap)

                if ((cnt == 1) && (debug)) {
                    val debugMutableMap: MutableMap<EnumEvent, String> = EnumMap(EnumEvent::class.java)
                    val debugEventStart = Time(context).getDateFormat((Time(context).getUnixTime()) + TIME_MINUTE * 2)
                    val debugEventEnd = Time(context).getDateFormat((Time(context).getUnixTime()) + TIME_MINUTE * 5)

                    debugMutableMap[EnumEvent.TITLE] = DEBUG_EVENT_LOAD_TEST_TITLE
                    debugMutableMap[EnumEvent.LINK] = DEBUG_EVENT_LOAD_TEST_LINK
                    debugMutableMap[EnumEvent.START] = debugEventStart
                    debugMutableMap[EnumEvent.END] = debugEventEnd
                    debugMutableMap[EnumEvent.CONTENT] = eventContent
                    debugMutableMap[EnumEvent.PATTER] = TIME_FORMAT_GLOBAL

                    Events(context).saveEvent(debugMutableMap)
                }

                if (debug) {
                    Log.d(DEBUG_LOAD_EVENT, "Incoming Event $i is: ${mutableMap[EnumEvent.TITLE]} from ${mutableMap[EnumEvent.START]} to ${mutableMap[EnumEvent.END]} with link ${mutableMap[EnumEvent.LINK]} \n")
                }
            }
        } catch (e: IOException) {
            Log.e(DEBUG_LOAD_EVENT, e.message.toString())
        }

        // Show new Notifications
        BackgroundNotification(context).newNotifications()
    }
}