package de.linkelisteortenau.app.backend.sql.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Delete things from the Event SQL-Database.
 **/
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.debug.*
import de.linkelisteortenau.app.backend.sql.COLUMN_EVENT_END
import de.linkelisteortenau.app.backend.sql.EVENTS_TABLE_NAME

/**
 * Class to delete things from the Event SQL-Database.
 *
 * @param context as Context
 **/
class EventDeleteDB(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = EventDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    /**
     * Delete events when there end are older as current unix timestamp.
     *
     * @param currentUnixTime as current Unix Timestamp as String
     **/
    fun deleteEvent(
        currentUnixTime: String?
    ) {
        // Define 'where' part of query.
        val whereClause = buildString {
            append(COLUMN_EVENT_END)
            append(" <= ")
            append(currentUnixTime)
        }

        // Issue SQL statement.
        val result = db.delete(EVENTS_TABLE_NAME, whereClause, null)

        // Debug
        if ((result == -1) && debug) {
            Log.e(DEBUG_SQL_EVENT_DELETE, DEBUG_SQL_EVENT_DELETE_FAILED)
        } else if (debug){
            Log.w(DEBUG_SQL_EVENT_DELETE, DEBUG_SQL_EVENT_DELETE_SUCCESS)
        }
    }

    /**
     * Remove duplicate Events.
     **/
    fun deleteDuplicatesInSQL() {
        val result = db.execSQL("DELETE FROM events_table_lilo WHERE EXISTS ( SELECT 1 FROM events_table_lilo p2 WHERE events_table_lilo.title = p2.title AND events_table_lilo.start = p2.start AND events_table_lilo.rowid > p2.rowid);")
        db.close()

        // Debug
        if (result.equals(-1) && debug) {
            Log.e(DEBUG_SQL_EVENT_DELETE, DEBUG_SQL_EVENT_DELETE_DUPLICATES_FAILED)
        } else if (debug){
            Log.d(DEBUG_SQL_EVENT_DELETE, DEBUG_SQL_EVENT_DELETE_DUPLICATES_SUCCESS)
        }
    }

    /**
     * Delete the hole Event SQL-Database
     **/
    fun deleteSQL() {
        context.deleteDatabase(EventDBHelper(context).databaseName)
    }
}
