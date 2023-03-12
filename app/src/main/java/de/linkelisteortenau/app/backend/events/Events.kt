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
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_CHECK_NOT_VALIDATED
import de.linkelisteortenau.app.backend.debug.DEBUG_EVENT_IN_PASTE
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.sql.events.EventDeleteDB
import de.linkelisteortenau.app.backend.sql.events.EventGetDB
import de.linkelisteortenau.app.backend.sql.events.EventInsertDB
import de.linkelisteortenau.app.backend.sql.events.EventUpdateDB
import de.linkelisteortenau.app.backend.time.EnumTime
import de.linkelisteortenau.app.backend.time.Time
import de.linkelisteortenau.app.ui.events.list_view.DataEvents
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
 * Class for the Events to load, save, check and delete.
 *
 * @param context as Context
 **/
class Events(val context: Context) {
    val debug = Preferences(context).getSystemDebug()

    /**
     * Check all Events delete Duplicates and
     * delete all Events when it end is older as current time.
     **/
    fun check() {
        val currentTime: Long = Time(context).getUnixTime()

        // Delete if the Event is to old
        deleteEvents(currentTime)

        // Check duplicates in Events and delete them.
        deleteDuplicates()

        if (debug) {
            val array: ArrayList<HashMap<EnumEvent, String>> = getEvents()

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
        event: HashMap<EnumEvent, String>
    ) {
        val eventStartAsLong = Time(context).dateFormatToUnix(event[EnumEvent.PATTER].toString(), event[EnumEvent.START].toString())
        val eventEndAsLong = Time(context).dateFormatToUnix(event[EnumEvent.PATTER].toString(), event[EnumEvent.END].toString())
        val hashMap: HashMap<EnumEvent, String> = HashMap<EnumEvent, String>()

        hashMap[EnumEvent.TITLE] = event[EnumEvent.TITLE].toString()
        hashMap[EnumEvent.CONTENT] = event[EnumEvent.CONTENT].toString()
        hashMap[EnumEvent.START] = eventStartAsLong.toString()
        hashMap[EnumEvent.END] = eventEndAsLong.toString()
        hashMap[EnumEvent.LINK] = event[EnumEvent.LINK].toString()
        hashMap[EnumEvent.FLAG] = event[EnumEvent.FLAG].toString()

        // Query Events
        val queryEvent = EventGetDB(context).queryData(hashMap)

        // Check the SQL and event end
        if (eventEndAsLong != null) {
            // Save if not exist in DB and Event end is in the future
            if ((!queryEvent) && (eventEndAsLong > Time(context).getUnixTime())) {
                setEvent(hashMap)
            }

            // Set Event Flag to true if this is already in the DB and the Event end is in the future
            else if ((queryEvent) && (eventEndAsLong > Time(context).getUnixTime())) {
                if (eventStartAsLong != null) {
                    if (debug) {
                        Log.d(DEBUG_EVENT, "$DEBUG_EVENT_ALREADY_SAVED\"${event[EnumEvent.TITLE]}\"")
                    }
                    setEventFlag(hashMap)
                }

            } else if (debug) {
                Log.d(DEBUG_EVENT, "$DEBUG_EVENT_IN_PASTE: \"${event[EnumEvent.TITLE]}\" \n \n")
            }
        }
    }

    /**
     * Save the new Event.
     *
     * @param event as HashMap with EnumEvent<String>
     **/
    private fun setEvent(
        event: HashMap<EnumEvent, String>
    ) {
        EventInsertDB(context).insertEvent(event)
    }

    /**
     * Set every event to false.
     *
     * @param event as HashMap with EnumEvent<String>
     **/
    fun setAllEventFlags(
        event: HashMap<EnumEvent, String>
    ) {
        if (!event[EnumEvent.FLAG].toBoolean()) {
            EventUpdateDB(context).setAllEventFlags(event)
        }

        // Debug
        if (debug) {
            val array: ArrayList<HashMap<EnumEvent, String>> = getEvents()
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
        event: HashMap<EnumEvent, String>,
    ) {
        EventUpdateDB(context).setEventFlag(event)
    }

    /**
     * Get all Events from SQL as Array List <String>.
     *
     * @return ArrayList as HashMap, EnumEvent as String
     **/
    private fun getEvents(): ArrayList<HashMap<EnumEvent, String>> {
        return EventGetDB(context).readData()
    }

    /**
     * Get all Events from SQL as Data List.
     *
     * @return List as DataEvents
     **/
    fun getEventsAsDataList(): List<DataEvents> {
        val array: ArrayList<HashMap<EnumEvent, String>> = getEvents()
        val list = mutableListOf<DataEvents>()

        for (i in 0 until array.size) {
            val hashMap = array[i]

            // Check Event to DataSource-List
            if (hashMap[EnumEvent.TITLE].toString().isEmpty() || hashMap[EnumEvent.START].toString().isBlank()) {
                if (debug) {
                    Log.e(DEBUG_EVENT, "$DEBUG_EVENT_CHECK_NOT_VALIDATED \n\n")
                }
            } else {

                val eventStartAsHashMap = Time(context).dateFormatToHumanTime(hashMap[EnumEvent.START].toString().toLong())
                val eventEndAsHashMap = Time(context).dateFormatToHumanTime(hashMap[EnumEvent.END].toString().toLong())
                val eventWeekday = Time(context).dateFormatToHumanTime(hashMap[EnumEvent.START].toString().toLong())
                val eventDate = "${eventStartAsHashMap[EnumTime.DAY]}.${eventStartAsHashMap[EnumTime.MONTH]}"
                val eventStart = "${eventStartAsHashMap[EnumTime.HOUR]}:${eventStartAsHashMap[EnumTime.MINUTE]}"

                val clock = context.getString(R.string.event_recycler_event_clock)
                val eventEnd = "${eventEndAsHashMap[EnumTime.HOUR]}:${eventEndAsHashMap[EnumTime.MINUTE]} $clock"

                val newEvent = DataEvents(
                    Random.nextLong(),
                    hashMap[EnumEvent.TITLE].toString(),
                    eventWeekday[EnumTime.WEEKDAY].toString(),
                    eventDate,
                    eventStart,
                    eventEnd,
                    hashMap[EnumEvent.LINK].toString()
                )
                list.add(newEvent)
            }
        }

        if (debug) {
            Log.d(DEBUG_EVENT, "Events DataAsList: \"$list\" \n\n")
        }
        return list
    }

    /**
     * Delete Event from SQL-Database that is older that the param time.
     *
     * @param time as Long that define the time. Delete the Event that is before this Time.
     **/
    private fun deleteEvents(
        time: Long
    ) {
        EventDeleteDB(context).deleteEvent(time.toString())
    }

    /**
     * Delete duplicates in SQL-Database.
     **/
    private fun deleteDuplicates() {
        EventDeleteDB(context).deleteDuplicatesInSQL()
    }

    /**
     * Get the next Events from the SQL-Database.
     **/
    fun getNotification(): HashMap<EnumEventNotification, String> {
        val notificationHashMap: HashMap<EnumEventNotification, String> = HashMap<EnumEventNotification, String>()
        val array: ArrayList<HashMap<EnumEvent, String>> = getEvents()

        // Std Variables for Event checker
        val multiplicator = Preferences(context).getUserEventsNotificationTimeMultiplicator()
        val range: Long = NOTIFICATION_EVENT_TIME_RANGE_MIN.toLong() * 60000 // Convert to millis

        for (i in 0 until array.size) {
            val hashMap = array[i]

            // Check Event for the given time-range to Push-Notification
            if (hashMap[EnumEvent.END].toString().toLong() >= Time(context).getUnixTime() &&
                ((hashMap[EnumEvent.START].toString().toLong() - (range * multiplicator)) <= Time(context).getUnixTime())
            ) {
                val eventStartAsHashMap = Time(context).dateFormatToHumanTime(hashMap[EnumEvent.START].toString().toLong())

                notificationHashMap[EnumEventNotification.TITLE] = hashMap[EnumEvent.TITLE].toString()

                notificationHashMap[EnumEventNotification.CONTENT] = GLOBAL_NULL
                notificationHashMap[EnumEventNotification.YEAR] = eventStartAsHashMap[EnumTime.YEAR].toString()
                notificationHashMap[EnumEventNotification.MONTH] = eventStartAsHashMap[EnumTime.MONTH].toString()
                notificationHashMap[EnumEventNotification.DAY] = eventStartAsHashMap[EnumTime.DAY].toString()
                notificationHashMap[EnumEventNotification.WEEKDAY] = eventStartAsHashMap[EnumTime.WEEKDAY].toString()
                notificationHashMap[EnumEventNotification.HOUR] = eventStartAsHashMap[EnumTime.HOUR].toString()
                notificationHashMap[EnumEventNotification.MINUTE] = eventStartAsHashMap[EnumTime.MINUTE].toString()
                notificationHashMap[EnumEventNotification.LINK] = hashMap[EnumEvent.LINK].toString()
                notificationHashMap[EnumEventNotification.FLAG] = hashMap[EnumEvent.FLAG].toString()

                if (debug) {
                    Log.d(
                        DEBUG_EVENT,
                        "Next Event is: \"${hashMap[EnumEvent.TITLE].toString()}\" Current Unix Time: \"${Time(context).getUnixTime()}\" Next Events end time: \"${hashMap[EnumEvent.END].toString()}\" \n \n"
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
        LoadEventFromServer(context).loadEvents()
    }

    /**
     * Delete the hole Event SQL-Database
     */
    fun deleteDatabase(){
        EventDeleteDB(context).deleteSQL()
    }
}