package de.linkelisteortenau.app.backend.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 *
 * Classes for the Events to load, save, check and delete.
 **/
import android.content.Context
import android.util.Log
import de.linkelisteortenau.app.*
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_ALREADY_SAVED
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_IN_PASTE
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.sql.events.EventDeleteDB
import de.linkelisteortenau.app.backend.sql.events.EventGetDB
import de.linkelisteortenau.app.backend.sql.events.EventInsertDB
import de.linkelisteortenau.app.backend.sql.events.EventUpdateDB
import de.linkelisteortenau.app.backend.time.EnumTime
import de.linkelisteortenau.app.backend.time.Time
import java.util.EnumMap
import kotlin.random.Random

/**
 * Global Enum for Events.
 */
enum class EnumEvent { TITLE, CONTENT, START, END, LINK, FLAG, PATTER }

/**
 * Global Enum for Events Notification
 */
enum class EnumEventNotification { TITLE, CONTENT, YEAR, MONTH, DAY, WEEKDAY, HOUR, MINUTE, LINK, FLAG }

/**
 * Data Class Event
 */
data class Event(
    val title: String,
    val content: String,
    val start: String,
    val end: String,
    val link: String,
    val flag: String
)

/**
 * Class for the Events to load, save, check and delete.
 *
 * @param context as Context
 **/
class Events(
    val context: Context
    ) {
    private val debug = Preferences(context).getSystemDebug()
    private val time = Time(context)
    private val preferences = Preferences(context)
    private val eventGetDB = EventGetDB(context)
    private val eventInsertDB = EventInsertDB(context)
    private val eventUpdateDB = EventUpdateDB(context)
    private val eventDeleteDB = EventDeleteDB(context)
    private val loadEventFromServer = LoadEventFromServer(context)

    /**
     * Check all Events delete Duplicates and
     * delete all Events when it end is older as current time.
     **/
    fun check() {
        val currentTime: Long = time.getUnixTime()

        // Delete if the Event is to old
        deleteEvents(currentTime)

        // Check duplicates in Events and delete them.
        deleteDuplicates()

        if (debug) {
            val array: ArrayList<MutableMap<EnumEvent, String>> = getEvents()

            for (i in 0 until array.size) {
                val hashMap = array[i]

                Log.d(DEBUG_EVENT, "Check Event $i with \"${hashMap[EnumEvent.TITLE].toString()}\" has flag: \"${hashMap[EnumEvent.FLAG].toString()}\"")
            }
            Log.d(DEBUG_EVENT, "\n\n")
        }
    }

    /**
     * Save event param to SQL. Convert Time to Unix long.
     * Patter has "yyyy-MM-dd'T'HH:mm:ssXXX".
     * Check that the event is not in the SQL and not in the future.
     *
     * @param event as HashMap with EnumEvent<String>
     **/
    @Suppress("SameParameterValue")
    fun saveEvent(
        event: MutableMap<EnumEvent, String>
    ) {
        val eventStartAsLong = time.dateFormatToUnix(event[EnumEvent.PATTER].toString(), event[EnumEvent.START].toString())
        val eventEndAsLong = time.dateFormatToUnix(event[EnumEvent.PATTER].toString(), event[EnumEvent.END].toString()) ?: return
        val mutableMap: MutableMap<EnumEvent, String> = EnumMap<EnumEvent, String>(EnumEvent::class.java)

        mutableMap[EnumEvent.TITLE]     = event[EnumEvent.TITLE].toString()
        mutableMap[EnumEvent.CONTENT]   = event[EnumEvent.CONTENT].toString()
        mutableMap[EnumEvent.START]     = eventStartAsLong.toString()
        mutableMap[EnumEvent.END]       = eventEndAsLong.toString()
        mutableMap[EnumEvent.LINK]      = event[EnumEvent.LINK].toString()
        mutableMap[EnumEvent.FLAG]      = event[EnumEvent.FLAG].toString()

        // Check the SQL and event end
        if ((!eventGetDB.queryData(mutableMap)) && (eventEndAsLong > time.getUnixTime())) {
            setEvent(mutableMap)
        }

        // Set Event Flag to true if this is already in the DB and the Event end is in the future
        else if ((eventGetDB.queryData(mutableMap)) && (eventEndAsLong > time.getUnixTime())) {
            if (eventStartAsLong != null) {
                if (debug) {
                    Log.d(DEBUG_EVENT, "$DEBUG_EVENT_ALREADY_SAVED\"${event[EnumEvent.TITLE]}\"")
                }
                setEventFlag(mutableMap)
            }

        } else if (debug) {
            Log.d(DEBUG_EVENT, "$DEBUG_EVENT_IN_PASTE: \"${event[EnumEvent.TITLE]}\" \n \n")
        }
    }

    /**
     * Save the new Event.
     *
     * @param event as HashMap with EnumEvent<String>
     **/
    private fun setEvent(
        event: MutableMap<EnumEvent, String>
    ) {
        eventInsertDB.insertEvent(event)
    }

    /**
     * Set every event to false.
     *
     * @param event as HashMap with EnumEvent<String>
     **/
    fun setAllEventFlags(
        event: MutableMap<EnumEvent, String>
    ) {
        if (event[EnumEvent.FLAG].toBoolean().not()) {
            eventUpdateDB.setAllEventFlags(event)
        }

        // Debug
        if (debug) {
            val array: ArrayList<MutableMap<EnumEvent, String>> = getEvents()
            for (i in 0 until array.size) {
                val hashMap = array[i]

                Log.d(DEBUG_EVENT, "Event $i with \"${hashMap[EnumEvent.TITLE].toString()}\" has flag: \"${hashMap[EnumEvent.FLAG].toString()}\"")
            }
            Log.d(DEBUG_EVENT, "\n\n")
        }
    }

    /**
     * Set one Event Flag to value.
     * When a event, in the SQL, is already saved than set it to true.
     *
     * @param event as HashMap with EnumEvent<String>
     **/
    private fun setEventFlag(
        event: MutableMap<EnumEvent, String>,
    ) {
        eventUpdateDB.setEventFlag(event)
    }

    /**
     * Get all Events from SQL as Array List <String>.
     *
     * @return ArrayList as HashMap, EnumEvent as String
     **/
    private fun getEvents(): ArrayList<MutableMap<EnumEvent, String>> {
        return eventGetDB.readData()
    }

    /**
     * Get all Events from SQL as Data List.
     *
     * @return List as DataEvents
     **/
    fun getEventsAsDataList(): List<DataEvents> {
        val events = getEvents()
            .filter { it[EnumEvent.TITLE]?.isNotBlank() == true && it[EnumEvent.START]?.isNotBlank() == true }
            .mapNotNull { event ->
                val startAsHashMap = time.dateFormatToHumanTime(event[EnumEvent.START]?.toLongOrNull() ?: return@mapNotNull null)
                val endAsHashMap = time.dateFormatToHumanTime(event[EnumEvent.END]?.toLongOrNull() ?: return@mapNotNull null)
                val weekday = time.dateFormatToHumanTime(event[EnumEvent.START]?.toLongOrNull() ?: return@mapNotNull null)
                val date = "${startAsHashMap[EnumTime.DAY]}.${startAsHashMap[EnumTime.MONTH]}"
                val start = "${startAsHashMap[EnumTime.HOUR]}:${startAsHashMap[EnumTime.MINUTE]}"
                val end = "${endAsHashMap[EnumTime.HOUR]}:${endAsHashMap[EnumTime.MINUTE]} ${context.getString(R.string.event_recycler_event_clock)}"
                DataEvents(
                    Random.nextLong(),
                    event[EnumEvent.TITLE]!!,
                    weekday[EnumTime.WEEKDAY]!!,
                    date,
                    start,
                    end,
                    event[EnumEvent.LINK] ?: ""
                )
            }
        if (debug) {
            Log.d(DEBUG_EVENT, "Events DataAsList: \"$events\" \n\n")
        }
        return events
    }

    /**
     * Delete Event from SQL-Database that is older that the param time.
     *
     * @param time as Long that define the time. Delete the Event that is before this Time.
     **/
    private fun deleteEvents(
        time: Long
    ) {
        eventDeleteDB.deleteEvent(time.toString())
    }

    /**
     * Delete duplicates in SQL-Database.
     **/
    private fun deleteDuplicates() {
        eventDeleteDB.deleteDuplicatesInSQL()
    }

    /**
     * Get the next Events from the SQL-Database.
     **/
    fun getNotification(): MutableMap<EnumEventNotification, String> {
        val notificationHashMap: MutableMap<EnumEventNotification, String> = EnumMap(EnumEventNotification::class.java)
        val array = getEvents()

        // Std Variables for Event checker
        val multiplicator = preferences.getUserEventsNotificationTimeMultiplicator()
        val range: Long = NOTIFICATION_EVENT_TIME_RANGE_MIN.toLong() * 60000 // Convert to millis
        val currentTime = time.getUnixTime()

        for (i in 0 until array.size) {
            val mutableMap = array[i]

            // Check Event for the given time-range to Push-Notification
            if ((mutableMap[EnumEvent.END]?.toLongOrNull() ?: 0) >= currentTime &&
                (((mutableMap[EnumEvent.START]?.toLongOrNull() ?: 0) - (range * multiplicator)) <= currentTime)
            ) {
                val eventStartAsHashMap = time.dateFormatToHumanTime(mutableMap[EnumEvent.START]?.toLongOrNull() ?: 0)

                notificationHashMap[EnumEventNotification.TITLE]    = mutableMap[EnumEvent.TITLE].toString()
                notificationHashMap[EnumEventNotification.CONTENT]  = GLOBAL_NULL
                notificationHashMap[EnumEventNotification.YEAR]     = eventStartAsHashMap[EnumTime.YEAR].toString()
                notificationHashMap[EnumEventNotification.MONTH]    = eventStartAsHashMap[EnumTime.MONTH].toString()
                notificationHashMap[EnumEventNotification.DAY]      = eventStartAsHashMap[EnumTime.DAY].toString()
                notificationHashMap[EnumEventNotification.WEEKDAY]  = eventStartAsHashMap[EnumTime.WEEKDAY].toString()
                notificationHashMap[EnumEventNotification.HOUR]     = eventStartAsHashMap[EnumTime.HOUR].toString()
                notificationHashMap[EnumEventNotification.MINUTE]   = eventStartAsHashMap[EnumTime.MINUTE].toString()
                notificationHashMap[EnumEventNotification.LINK]     = mutableMap[EnumEvent.LINK].toString()
                notificationHashMap[EnumEventNotification.FLAG]     = mutableMap[EnumEvent.FLAG].toString()

                if (debug) {
                    Log.d(
                        DEBUG_EVENT,
                        "Next Event is: \"${mutableMap[EnumEvent.TITLE].toString()}\" Current Unix Time: \"${time.getUnixTime()}\" Next Events end time: \"${mutableMap[EnumEvent.END].toString()}\" \n \n"
                    )
                }
            }
        }
        return notificationHashMap
    }

    /**
     * Load all Events from https://www.Linke-Liste-Ortenau/termine/
     *
     * @see <a href="https://www.linke-liste-ortenau.de/termine/">Events Page</a>
     * @see <a href="https://www.linke-liste-ortenau.de/feed/eo-events/">Events Calendar</a>
     **/
    fun loadEventsFromServer() {
        loadEventFromServer.loadEvents()
    }

    /**
     * Delete the hole Event SQL-Database
     */
    fun deleteDatabase(){
        eventDeleteDB.deleteSQL()
    }
}