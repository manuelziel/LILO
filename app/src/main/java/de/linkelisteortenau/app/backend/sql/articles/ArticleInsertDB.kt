package de.linkelisteortenau.app.backend.sql.articles

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * Insert things to the Article SQL-Database
 **/
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.articles.EnumArticle
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_ARTICLE_INSERT
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_ARTICLE_INSERT_FAILED
import de.linkelisteortenau.app.backend.debug.DEBUG_SQL_ARTICLE_INSERT_SUCCESS
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class to write Content to the Article SQL-Database
 *
 * @param context as Context
 **/
class ArticleInsertDB(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = ArticleDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    /**
     * Write Article and this Content to SQL-Database
     *
     * @param article as HashMap with EnumArticles<String>
     **/
    fun insertArticle(
        article: HashMap<EnumArticle, String>
    ): Boolean {
        val values = ContentValues()
        values.put(COLUMN_ARTICLE_TITLE, article[EnumArticle.TITLE])
        values.put(COLUMN_ARTICLE_LINK, article[EnumArticle.LINK])
        values.put(COLUMN_ARTICLE_CONTENT, article[EnumArticle.CONTENT])
        values.put(COLUMN_ARTICLE_FLAG, article[EnumArticle.FLAG])

        val result = db.insert(ARTICLES_TABLE_NAME, null, values).toInt()

        return if (result == -1) {
            if (debug) {
                Log.e(DEBUG_SQL_ARTICLE_INSERT, DEBUG_SQL_ARTICLE_INSERT_FAILED+"$values")
            }
            true
        } else {
            if (debug) {
                Log.d(DEBUG_SQL_ARTICLE_INSERT, DEBUG_SQL_ARTICLE_INSERT_SUCCESS+"$values")
            }
            false
        }
    }
}