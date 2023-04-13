package de.linkelisteortenau.app.backend.sql.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Update things in the SQL Database
 **/
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.debug.*
import de.linkelisteortenau.app.backend.events.EnumEvent
import de.linkelisteortenau.app.backend.sql.COLUMN_EVENT_END
import de.linkelisteortenau.app.backend.sql.COLUMN_EVENT_FLAG
import de.linkelisteortenau.app.backend.sql.COLUMN_EVENT_START
import de.linkelisteortenau.app.backend.sql.EVENTS_TABLE_NAME

/**
 * Class to update Content in the Event SQL-Database
 **/
class EventUpdateDB(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = EventDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    /**
     * Set all event Flags to new Value
     *
     * @param event as MutableMap with EnumEvent<String>
     **/
    fun setAllEventFlags(
        event: MutableMap<EnumEvent, String>
    ) {
        val result = db.execSQL("UPDATE $EVENTS_TABLE_NAME SET $COLUMN_EVENT_FLAG='flag', $COLUMN_EVENT_FLAG='${event[EnumEvent.FLAG]}' ")
        db.close()

        if (result.equals(-1) && debug) {
            Log.e(DEBUG_SQL_EVENT_UPDATE, "$DEBUG_SQL_EVENT_UPDATE_ALL_FAILED\"${event[EnumEvent.FLAG]}\"")
        } else if (debug){
            Log.d(DEBUG_SQL_EVENT_UPDATE, "$DEBUG_SQL_EVENT_UPDATE_ALL_SUCCESS\"${event[EnumEvent.FLAG]}\"")
        }
    }

    /**
     * Set one Event Flag to new Value
     *
     * @param event as MutableMap with EnumEvent<String>
     **/
    fun setEventFlag(
        event: MutableMap<EnumEvent, String>
    ) {
        val values = ContentValues()
        values.put(COLUMN_EVENT_FLAG, event[EnumEvent.FLAG])

        val whereClause = buildString {
            append(COLUMN_EVENT_START)
            append(" = ? AND ")
            append(COLUMN_EVENT_END)
            append(" = ?")
        }

        val result = db.update(EVENTS_TABLE_NAME, values, whereClause, arrayOf(event[EnumEvent.START], event[EnumEvent.END]))
        db.close()

        // Debug
        if (result == -1 && debug) {
            Log.e(DEBUG_SQL_EVENT_UPDATE, "$DEBUG_SQL_EVENT_UPDATE_ONE_FAILED \"${event[EnumEvent.FLAG]}\" \n \n")
        } else if (debug){
            Log.d(DEBUG_SQL_EVENT_UPDATE, "$DEBUG_SQL_EVENT_UPDATE_ONE_SUCCESS \"${event[EnumEvent.FLAG]}\" \n \n")
        }
    }
}