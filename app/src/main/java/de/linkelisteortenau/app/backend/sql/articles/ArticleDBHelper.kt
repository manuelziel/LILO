package de.linkelisteortenau.app.backend.sql.articles

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * SQL Helper for the Article SQL-Database
 **/
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import de.linkelisteortenau.app.backend.debug.*

/**
 * SQL Object and Helper Class to create and upgrade the Article SQL-Database
 *
 * @param context as Context
 **/
class ArticleDBHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * SQL Object to create or check the Article Database
     **/
    companion object {
        const val DATABASE_NAME = "Article_LiLO.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            ("CREATE TABLE " + ARTICLES_TABLE_NAME + " ("
                    + ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ARTICLE_TITLE + " TEXT NOT NULL, "
                    + COLUMN_ARTICLE_LINK + " TEXT NOT NULL, "
                    + COLUMN_ARTICLE_CONTENT + " TEXT NOT NULL, "
                    + COLUMN_ARTICLE_FLAG + " TEXT NOT NULL);")

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS  $ARTICLES_TABLE_NAME"
    }

    /**
     * Check and create Article SQL-Database if they not exist
     * Lifecycle
     *
     * Activity lifecycle create
     * @see <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Activity Lifecycle</a>
     **/
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(DEBUG_SQL_ARTICLE_HELPER, DEBUG_SQL_ARTICLE_HELPER_CREATE_DB)
        db!!.execSQL(SQL_CREATE_ENTRIES)
    }

    /**
     * Upgrade the Article SQL-Database
     *
     * @param oldVersion
     * @param newVersion
     **/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(DEBUG_SQL_ARTICLE_HELPER, "$DEBUG_SQL_ARTICLE_HELPER_UPGRADE from $oldVersion to $newVersion")
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}