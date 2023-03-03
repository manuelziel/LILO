package de.linkelisteortenau.app.backend.sql.articles

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * Delete things from the Article SQL-Database.
 **/
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.BackgroundService
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.debug.*

/**
 * Class to delete things from the Article SQL-Database.
 *
 * @param context as Context
 **/
class ArticleDeleteDB(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = ArticleDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    /**
     * Delete Articles when there have an false Flag.
     *
     * @param flag as Boolean for the Articles has to delete
     **/
    fun deleteFlaggedArticles(
        flag: Boolean
    ) {
        // Define 'where' part of query.
        val whereClause = buildString {
            append(COLUMN_ARTICLE_FLAG)
            append(" == ")
            append(flag)
        }

        // Issue SQL Statement.
        val result = db.delete(ARTICLES_TABLE_NAME, whereClause, null)

        // Debug
        if ((result == -1) && debug) {
            Log.e(DEBUG_SQL_ARTICLE_DELETE, DEBUG_SQL_ARTICLE_DELETE_FAILED)
        } else if (debug){
            Log.w(DEBUG_SQL_ARTICLE_DELETE, DEBUG_SQL_ARTICLE_DELETE_SUCCESS)
        }
    }

    /**
     * Remove duplicate Articles.
     **/
    fun deleteDuplicatesInSQL() {
        val result = db.execSQL("DELETE FROM articles_table_lilo WHERE EXISTS ( SELECT 1 FROM articles_table_lilo p2 WHERE articles_table_lilo.title = p2.title AND articles_table_lilo.link = p2.link AND articles_table_lilo.rowid > p2.rowid);")
        db.close()

        // Debug
        if (result.equals(-1) && debug) {
            Log.e(DEBUG_SQL_ARTICLE_DELETE, DEBUG_SQL_ARTICLE_DELETE_DUPLICATES_FAILED)
        } else if (debug){
            Log.d(DEBUG_SQL_ARTICLE_DELETE, DEBUG_SQL_ARTICLE_DELETE_DUPLICATES_SUCCESS)
        }
    }

    /**
     * Delete the hole Article SQL-Database
     **/
    fun deleteSQL() {
        context.deleteDatabase(ArticleDBHelper(context).databaseName)
    }
}
