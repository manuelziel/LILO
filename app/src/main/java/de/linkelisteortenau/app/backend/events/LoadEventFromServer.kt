package de.linkelisteortenau.app.backend.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Classes for the Events to load, save, check and delete.
 **/
import android.content.Context
import android.util.Log
import de.linkelisteortenau.app.TIME_MINUTE
import de.linkelisteortenau.app.TIME_PATTER_GLOBAL
import de.linkelisteortenau.app.WEB_VIEW_URL_EVENTS
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_LOAD_TEST_LINK
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_LOAD_TEST_TITLE
import de.linkelisteortenau.app.backend.debug.DEBUG_LOAD_EVENT
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.time.Time
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

/**
 * Class to load Events from the Web Server
 **/
class LoadEventFromServer(val context: Context) {
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
            loadEventsFunction()
        }
    }

    /**
     * Load all Events with Jsoup lib from https://www.Linke-Liste-Ortenau.de/termine/
     * Session load timeout are disabled!
     * All Elements are stored in "div.entry-content <ul <li"
     *
     * Load the elements from every link. Every element are stored in div.entry-container header h1
     * @see <a href="https://www.linke-liste-ortenau.de/termine/">Events Page</a>
     * @see <a href="https://www.linke-liste-ortenau.de/feed/eo-events/">Events Calendar</a>
     **/
    private val eventContent = "null" // No Content implemented
    private fun loadEventsFunction() {
        try {
            val doc: Document = Jsoup.connect(WEB_VIEW_URL_EVENTS).timeout(0).get()
            val hashMap: HashMap<EnumEvent, String> = HashMap<EnumEvent, String>()

            if (doc.hasText()) {
                hashMap[EnumEvent.FLAG] = false.toString()
                Events(context).setAllEventFlags(hashMap) // Set all DB Event flags to false
                val eContent: Elements = doc.select("div.entry-content ul li")
                val eHref: Elements = eContent.select("a[href]")
                var cnt = 0

                if (eHref.hasText()) {
                    for (i in eHref) {
                        val hashMap: HashMap<EnumEvent, String> = HashMap<EnumEvent, String>()

                        // Get the Link
                        val eventLink = i.toString().substringAfter("href=\"").substringBefore("\">")

                        // Get content from link
                        val docCalRef: Document = Jsoup.connect(eventLink).timeout(0).get()

                        // Get title from content
                        val eTitleContent: Elements = docCalRef.select("div.entry-container header h1")
                        val eTitle: Elements = eTitleContent.select("h1[class]")
                        val eventTitle = eTitle.toString().substringAfter("entry-title\">").substringBefore("</h1")

                        // Get start time and end time from content
                        val eTimeMeta: Elements = docCalRef.select("div.eventorganiser-event-meta ul li")
                        val eTime: Elements = eTimeMeta.select("time[datetime]")

                        val eventStart = eTime.toString().substringAfter("<time itemprop=\"startDate\" datetime=\"").substringBefore("\">")
                        val eventEnd = eTime.toString().substringAfter("<time itemprop=\"endDate\" datetime=\"").substringBefore("\">")

                        hashMap[EnumEvent.TITLE] = eventTitle
                        hashMap[EnumEvent.LINK] = eventLink
                        hashMap[EnumEvent.START] = eventStart
                        hashMap[EnumEvent.END] = eventEnd
                        hashMap[EnumEvent.CONTENT] = eventContent
                        hashMap[EnumEvent.FLAG] = true.toString()
                        hashMap[EnumEvent.PATTER] = TIME_PATTER_GLOBAL

                        cnt++
                        if ((cnt == 1) && (debug)) {
                            val debugHashMap: HashMap<EnumEvent, String> = HashMap<EnumEvent, String>()
                            val debugEventStart = Time(context).getDateFormat((Time(context).getUnixTime()) + TIME_MINUTE * 2)
                            val debugEventEnd = Time(context).getDateFormat((Time(context).getUnixTime()) + TIME_MINUTE * 5)

                            debugHashMap[EnumEvent.TITLE] = DEBUG_EVENT_LOAD_TEST_TITLE
                            debugHashMap[EnumEvent.LINK] = DEBUG_EVENT_LOAD_TEST_LINK
                            debugHashMap[EnumEvent.START] = debugEventStart
                            debugHashMap[EnumEvent.END] = debugEventEnd
                            debugHashMap[EnumEvent.CONTENT] = eventContent
                            debugHashMap[EnumEvent.FLAG] = true.toString()
                            debugHashMap[EnumEvent.PATTER] = TIME_PATTER_GLOBAL

                            Events(context).saveEvent(debugHashMap)
                        }

                        if (debug) {
                            Log.d(DEBUG_LOAD_EVENT, "Incoming ${eHref.size} Events $cnt is: $eventTitle from $eventStart to $eventEnd with link $eventLink \n")
                        }

                        Events(context).saveEvent(hashMap)

                        if (cnt == eHref.size) {
                            // TODO Not yet implemented
                        }
                    }
                }
            }
        } catch (e: IOException) {
            Log.e(DEBUG_LOAD_EVENT, e.message.toString())
        }
    }

    /**
     * Load all Events with JSON-LD from https://www.Linke-Liste-Ortenau.de/termine/
     * Session load timeout are disabled!
     *
     * @see <a href="https://www.linke-liste-ortenau.de/termine/">Events Page</a>
     **/
    fun loadJSONFunction() {
        try {
            val doc: Document = Jsoup.connect("https://www.linke-liste-ortenau.de/events/").timeout(0).get()
            var cnt = 0

            // In this case you want the first script tag
            val e = doc.select("script[type=application/ld+json]")
            val array = JSONArray(e.html())
            val lenght = array.length() - 1

            for (i in 0..lenght) {
                cnt++
                val hashMap: HashMap<EnumEvent, String> = HashMap<EnumEvent, String>()
                val obj = array.getJSONObject(i)

                hashMap[EnumEvent.TITLE] = obj.get("name").toString()
                hashMap[EnumEvent.START] = obj.get("startDate").toString()
                hashMap[EnumEvent.END] = obj.get("endDate").toString()
                hashMap[EnumEvent.LINK] = obj.get("url").toString()
                hashMap[EnumEvent.CONTENT] = eventContent
                hashMap[EnumEvent.FLAG] = false.toString()
                hashMap[EnumEvent.PATTER] = TIME_PATTER_GLOBAL

                // Set all Event Flags and Save Event
                Events(context).setAllEventFlags(hashMap) // Set all DB Event flags to false
                Events(context).saveEvent(hashMap)

                if ((cnt == 1) && (debug)) {
                    val debugHashMap: HashMap<EnumEvent, String> = HashMap<EnumEvent, String>()
                    val debugEventStart = Time(context).getDateFormat((Time(context).getUnixTime()) + TIME_MINUTE * 2)
                    val debugEventEnd = Time(context).getDateFormat((Time(context).getUnixTime()) + TIME_MINUTE * 5)

                    debugHashMap[EnumEvent.TITLE] = DEBUG_EVENT_LOAD_TEST_TITLE
                    debugHashMap[EnumEvent.LINK] = DEBUG_EVENT_LOAD_TEST_LINK
                    debugHashMap[EnumEvent.START] = debugEventStart
                    debugHashMap[EnumEvent.END] = debugEventEnd
                    debugHashMap[EnumEvent.CONTENT] = eventContent
                    debugHashMap[EnumEvent.PATTER] = TIME_PATTER_GLOBAL

                    Events(context).saveEvent(debugHashMap)
                }

                if (debug) {
                    Log.d(DEBUG_LOAD_EVENT, "Incoming Event $i is: ${hashMap[EnumEvent.TITLE]} from ${hashMap[EnumEvent.START]} to ${hashMap[EnumEvent.END]} with link ${hashMap[EnumEvent.LINK]} \n")
                }
            }
        } catch (e: IOException) {
            Log.e(DEBUG_LOAD_EVENT, e.message.toString())
        }
    }
}