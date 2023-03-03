package de.linkelisteortenau.app.backend.sql.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Insert things to the Event SQL-Database
 **/
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_EVENT_INSERT
import de.linkelisteortenau.app.backend.events.EnumEvent
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.sql.*

/**
 * Class to write Content to the Event Database
 *
 * @param context as Context
 **/
class EventInsertDB(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = EventDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    /**
     * Write events and this Content to Database
     *
     * @param event as HashMap EnumEvent<String>
     **/
    fun insertEvent(
        event: HashMap<EnumEvent, String>
    ) {
        val values = ContentValues()
        values.put(COLUMN_EVENT_TITLE, event[EnumEvent.TITLE])
        values.put(COLUMN_EVENT_START, event[EnumEvent.START])
        values.put(COLUMN_EVENT_END, event[EnumEvent.END])
        values.put(COLUMN_EVENT_LINK, event[EnumEvent.LINK])
        values.put(COLUMN_EVENT_CONTENT, event[EnumEvent.CONTENT])
        values.put(COLUMN_EVENT_FLAG, event[EnumEvent.FLAG])

        val result = db.insert(EVENTS_TABLE_NAME, null, values).toInt()
        db.close()

        if ((result == -1) && debug) {
            Log.e(DEBUG_SQL_EVENT_INSERT, "Insert Event to DB failed: $values")
        } else {
            //Log.d(DEBUG_SQL_INSERT, "Insert event to DB success: $values")
        }
    }
}