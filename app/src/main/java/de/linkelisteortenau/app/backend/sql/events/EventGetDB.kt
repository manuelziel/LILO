package de.linkelisteortenau.app.backend.sql.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-12
 *
 * Get things from the Event SQL-Database
 **/
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_EVENT_GET
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_EVENT_GET_EMPTY
import de.linkelisteortenau.app.backend.events.EnumEvent
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.sql.*
import java.util.EnumMap

/**
 * Class to get things from the Event SQL-Database
 *
 * @param context as Context
 **/
class EventGetDB(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = EventDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.readableDatabase

    /**
     * Read Events with content from the SQL-Database as ascending order from event start.
     *
     * @return ArrayList as String
     **/
    fun readData(): ArrayList<MutableMap<EnumEvent, String>> {
        val array = ArrayList<MutableMap<EnumEvent, String>>()

        val query = buildString {
            append("SELECT * FROM ")
            append(EVENTS_TABLE_NAME)
            append(" ORDER BY ")
            append(COLUMN_EVENT_START)
            append(" ASC")
        }

        val cursor: Cursor = db.rawQuery(query, null)

        if ((cursor.count == 0) && debug) {
            Log.d(DEBUG_SQL_EVENT_GET, DEBUG_SQL_EVENT_GET_EMPTY)
        }

        while (cursor.moveToNext()) {
            val mutableMap: MutableMap<EnumEvent, String> = EnumMap<EnumEvent, String>(EnumEvent::class.java)
            mutableMap[EnumEvent.TITLE] = cursor.getString(event_title_index)
            mutableMap[EnumEvent.START] = cursor.getString(event_start_index)
            mutableMap[EnumEvent.END] = cursor.getString(event_end_index)
            mutableMap[EnumEvent.LINK] = cursor.getString(event_link_index)
            mutableMap[EnumEvent.CONTENT] = cursor.getString(event_content_index)
            mutableMap[EnumEvent.FLAG] = cursor.getString(event_flag_index)

            array.add(mutableMap)

            if (debug) {
                Log.d(DEBUG_SQL_EVENT_GET, "Event from DB: \"$mutableMap\"")
            }
        }

        cursor.close()
        db.close()

        if (debug) {
            Log.d(DEBUG_SQL_EVENT_GET, "\n\n")
        }
        return array
    }

    /**
     * Query data from Event SQL-Database and return boolean true if query something
     *
     * @param event as MutableMap with EnumEvent<String>
     **/
    fun queryData(
        event: MutableMap<EnumEvent, String>
    ): Boolean {
        val debug = Preferences(context).getSystemDebug()
        val dbHelper = EventDBHelper(context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val query = buildString {
            append("SELECT * FROM ")
            append(EVENTS_TABLE_NAME)
            append(" WHERE ")
            append(COLUMN_EVENT_START)
            append(" = ? AND ")
            append(COLUMN_EVENT_END)
            append(" = ?")
        }

        val cursor = db.rawQuery(query, arrayOf(event[EnumEvent.START], event[EnumEvent.END]))

        val result = cursor.count > 0
        if (result && debug) {
            Log.d(DEBUG_SQL_EVENT_GET, "Query count: \"${cursor.count}\" by \"${event[EnumEvent.TITLE]}\"")
        }

        cursor.close()
        db.close()

        return result
    }
}