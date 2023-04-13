package de.linkelisteortenau.app.backend.sql.articles

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * Update things in the Article SQL Database
 **/
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.linkelisteortenau.app.backend.articles.EnumArticle
import de.linkelisteortenau.app.backend.debug.*
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Class to update Content in the Article SQL-Database
 *
 * @param context as Context.
 **/
class ArticleUpdateDB(val context: Context) {
    private val debug = Preferences(context).getSystemDebug()
    private val dbHelper = ArticleDBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    /**
     * Set all Article Flags to new Value
     *
     * @param flag as Boolean for the Flag
     **/
    fun setAllArticleFlags(
        flag: Boolean
    ): Boolean {
        var bool = false

        val result = db.execSQL("UPDATE $ARTICLES_TABLE_NAME SET $COLUMN_ARTICLE_FLAG='flag', $COLUMN_ARTICLE_FLAG='${flag}' ")

        if (result.equals(-1) && debug) {
            Log.e(DEBUG_SQL_ARTICLE_UPDATE, "$DEBUG_SQL_ARTICLE_UPDATE_ALL_FAILED\"${flag}\"")
            bool = false
        } else if (debug){
            Log.d(DEBUG_SQL_ARTICLE_UPDATE, "$DEBUG_SQL_ARTICLE_UPDATE_ALL_SUCCESS\"${flag}\"")
            bool = true
        }
        return bool
    }

    /**
     * Set one Article with new Value
     *
     * @param article as MutableMap with EnumArticles<String>
     **/
    fun setArticle(
        article: MutableMap<EnumArticle, String>
    ) {
        val values = ContentValues()
        values.put(COLUMN_ARTICLE_TITLE, article[EnumArticle.TITLE])
        values.put(COLUMN_ARTICLE_CONTENT, article[EnumArticle.CONTENT])
        values.put(COLUMN_ARTICLE_LINK, article[EnumArticle.LINK])
        values.put(COLUMN_ARTICLE_FLAG, article[EnumArticle.FLAG])

        val whereClause = buildString {
            append(COLUMN_ARTICLE_TITLE)
            append(" = ? AND ")
            append(COLUMN_ARTICLE_LINK)
            append(" = ?")
        }

        val result = db.update(ARTICLES_TABLE_NAME, values, whereClause, arrayOf(article[EnumArticle.TITLE], article[EnumArticle.LINK]))

        // Debug
        if (result == -1 && debug) {
            Log.e(DEBUG_SQL_ARTICLE_UPDATE, "$DEBUG_SQL_ARTICLE_UPDATE_ONE_FAILED \"${article[EnumArticle.FLAG]}\" \n \n")
        } else if (debug){
            Log.d(DEBUG_SQL_ARTICLE_UPDATE, "$DEBUG_SQL_ARTICLE_UPDATE_ONE_SUCCESS \"${article[EnumArticle.FLAG]}\" \n \n")
        }
    }
}