package de.linkelisteortenau.app.backend.sql.articles

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-12
 *
 * Get things from the Article SQL-Database
 **/
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.articles.EnumArticle
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_ARTICLE_GET
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_ARTICLE_GET_DB
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_ARTICLE_GET_EMPTY
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_ARTICLE_GET_FLAGGED_DB
import de.linkelisteortenau.app.backend.preferences.Preferences
import java.util.EnumMap

/**
 * Class to get things from the Article SQL-Database
 *
 * @param context as Context
 **/
class ArticleGetDB(
    val context: Context
) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = ArticleDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    /**
     * Read Article with content from the SQL-Database as ascending order from Article start.
     *
     * @return HashMap as EnumArticle<String>
     **/
    fun readData(): ArrayList<MutableMap<EnumArticle, String>> {
        val array = ArrayList<MutableMap<EnumArticle, String>>()

        val query = buildString {
            append("SELECT * FROM ")
            append(ARTICLES_TABLE_NAME)
        }

        val cursor: Cursor = db.rawQuery(query, null)

        if ((cursor.count == 0) && debug) {
            Log.d(DEBUG_SQL_ARTICLE_GET, DEBUG_SQL_ARTICLE_GET_EMPTY)
        }

        while (cursor.moveToNext()) {
            val mutableMap: MutableMap<EnumArticle, String> = EnumMap(EnumArticle::class.java)
            mutableMap[EnumArticle.TITLE] = cursor.getString(article_title_index)
            mutableMap[EnumArticle.LINK] = cursor.getString(article_link_index)
            mutableMap[EnumArticle.CONTENT] = cursor.getString(article_content_index)
            mutableMap[EnumArticle.FLAG] = cursor.getString(article_flag_index)

            array.add(mutableMap)

            if (debug) {
                Log.d(DEBUG_SQL_ARTICLE_GET, DEBUG_SQL_ARTICLE_GET_DB + "\"$mutableMap\"")
            }
        }
        cursor.close()

        if (debug) {
            Log.d(DEBUG_SQL_ARTICLE_GET, "\n\n")
        }
        return array
    }

    /**
     * Query data from Article SQL-Database and return boolean true if query something
     *
     * @param flag as Boolean for the Flag
     * @return ArrayList with HashMap as <EnumArticle, String>
     **/
    fun queryFlaggedArticles(
        flag: Boolean
    ): ArrayList<MutableMap<EnumArticle, String>> {
        val array = ArrayList<MutableMap<EnumArticle, String>>()

        val query = buildString {
            append("SELECT * FROM ")
            append(ARTICLES_TABLE_NAME)
            append(" WHERE ")
            append(COLUMN_ARTICLE_FLAG)
            append(" = ?")
        }

        val cursor = db.rawQuery(query, arrayOf(flag.toString()))

        val result = cursor.count > 0
        if (result && debug) {
            Log.d(DEBUG_SQL_ARTICLE_GET, "Query count: \"${cursor.count}\" with Flags \"${flag}\"")
        } else {
            //Log.e(DEBUG_SQL_ARTICLE_GET, "Query not found")
        }

        while (cursor.moveToNext()) {
            val mutableMap: MutableMap<EnumArticle, String> = EnumMap(EnumArticle::class.java)
            mutableMap[EnumArticle.TITLE] = cursor.getString(article_title_index)
            mutableMap[EnumArticle.LINK] = cursor.getString(article_link_index)
            mutableMap[EnumArticle.CONTENT] = cursor.getString(article_content_index)
            mutableMap[EnumArticle.FLAG] = cursor.getString(article_flag_index)

            array.add(mutableMap)

            if (debug) {
                Log.d(DEBUG_SQL_ARTICLE_GET, DEBUG_SQL_ARTICLE_GET_FLAGGED_DB + "\"$mutableMap\"")
            }
        }
        cursor.close()

        return array
    }

    /**
     * Query data from Article SQL-Database and return boolean true if query something
     *
     * @param article as HashMap<EnumArticle, String>
     * @return Boolean
     **/
    fun queryData(
        article: MutableMap<EnumArticle, String>
    ): Boolean {
        val query = buildString {
            append("SELECT * FROM ")
            append(ARTICLES_TABLE_NAME)
            append(" WHERE ")
            append(COLUMN_ARTICLE_TITLE)
            append(" = ? AND ")
            append(COLUMN_ARTICLE_LINK)
            append(" = ?")
        }

        val cursor = db.rawQuery(query, arrayOf(article[EnumArticle.TITLE], article[EnumArticle.LINK]))

        val result = cursor.count > 0
        if (result && debug) {
            Log.d(DEBUG_SQL_ARTICLE_GET, "Query count: \"${cursor.count}\" by \"${article[EnumArticle.TITLE]}\"")
        }
        cursor.close()

        return result
    }
}