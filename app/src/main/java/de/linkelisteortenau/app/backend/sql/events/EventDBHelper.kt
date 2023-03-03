package de.linkelisteortenau.app.backend.sql.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * SQL Helper for the Event SQL-Database
 **/
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import de.linkelisteortenau.app.backend.debug.*
import de.linkelisteortenau.app.backend.sql.*

/**
 * SQL Object and Helper Class to create and upgrade the Event SQL-Database
 *
 * @param context as Context
 **/
class EventDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * SQL Object to create or check the Event Database
     **/
    companion object {
        private const val DATABASE_NAME = "Event_LiLO.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            ("CREATE TABLE " + EVENTS_TABLE_NAME + " ("
                    + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_EVENT_TITLE + " TEXT NOT NULL, "
                    + COLUMN_EVENT_START + " TEXT NOT NULL, "
                    + COLUMN_EVENT_END + " TEXT NOT NULL, "
                    + COLUMN_EVENT_LINK + " TEXT NOT NULL, "
                    + COLUMN_EVENT_CONTENT + " TEXT NOT NULL, "
                    + COLUMN_EVENT_FLAG + " TEXT NOT NULL);")

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS  $EVENTS_TABLE_NAME"
    }

    /**
     * Check and create Event SQL-Database if they not exist
     * Lifecycle
     *
     * Activity lifecycle create
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     **/
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(DEBUG_SQL_EVENT_HELPER, DEBUG_SQL_EVENT_HELPER_CREATE_DB)
        db!!.execSQL(SQL_CREATE_ENTRIES)
    }

    /**
     * Upgrade the Event SQL-Database
     *
     * @param oldVersion
     * @param newVersion
     **/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(DEBUG_SQL_EVENT_HELPER, "$DEBUG_SQL_EVENT_HELPER_UPGRADE from $oldVersion to $newVersion")
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}