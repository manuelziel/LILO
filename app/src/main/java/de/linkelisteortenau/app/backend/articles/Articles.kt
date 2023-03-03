package de.linkelisteortenau.app.backend.articles

import android.content.Context
import android.util.Log
import de.linkelisteortenau.app.backend.debug.DEBUG_ARTICLE
import de.linkelisteortenau.app.backend.preferences.Preferences
import de.linkelisteortenau.app.backend.sql.articles.ArticleDeleteDB
import de.linkelisteortenau.app.backend.sql.articles.ArticleGetDB
import de.linkelisteortenau.app.backend.sql.articles.ArticleInsertDB
import de.linkelisteortenau.app.backend.sql.articles.ArticleUpdateDB

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * Classes for the Articles to load, save, check and delete
 **/

/**
 * Global Enum for Articles
 */
enum class EnumArticle { TITLE, CONTENT, LINK, FLAG }

/**
 * Class for the Articles to load, save, check and delete
 *
 * @param context as Context
 **/
class Articles (val context: Context){
    val debug = Preferences(context).getSystemDebug()

    /**
     * Check all Events delete Duplicates and
     * delete all Events when it end is older as current time.
     **/
    fun check() {
        // Check duplicates in Articles and delete them.
        deleteDuplicates()

        if (debug) {
            val array: ArrayList<HashMap<EnumArticle, String>> = getArticles()

            for (i in 0 until array.size) {
                val hashMap = array[i]

                Log.d(DEBUG_ARTICLE, "Check Articles $i with \"${hashMap[EnumArticle.TITLE].toString()}\" has flag: \"${hashMap[EnumArticle.FLAG].toString()}\"")
            }
            Log.d(DEBUG_ARTICLE, "\n\n")
        }
    }

    /**
     * Save and query the Article
     *
     * @param article as HashMap with EnumArticles<String>
     **/
    fun saveArticles(
        article: HashMap<EnumArticle, String>
    ) {
        // Query Article with the Title and Link
        val queryArticle = getQueryArticle(article)

        if ((!article[EnumArticle.TITLE].isNullOrBlank()
                    || !article[EnumArticle.CONTENT].isNullOrBlank()
                    || !article[EnumArticle.LINK].isNullOrBlank()
                    || !article[EnumArticle.FLAG].isNullOrBlank())
            && !queryArticle
        ) {
            // Save here
            setArticle(article)

            if (debug) {
                Log.d(
                    DEBUG_ARTICLE,
                    "\n \n"
                )
            }

        } else if (queryArticle) {
            // Article already in the SQL
            if (debug) {
                Log.d(
                    DEBUG_ARTICLE,
                    "Article already saved with " +
                            "Title: \"${article[EnumArticle.TITLE]}\" " +
                            "Content: \"${article[EnumArticle.CONTENT]} " +
                            "Link: \"${article[EnumArticle.LINK]}\" " +
                            "Flag: \"${article[EnumArticle.FLAG]}\" \n \n"
                )
            }
        } else {
            // Article NULL or Blank
            if (debug) {
                Log.e(
                    DEBUG_ARTICLE,
                    "Save Article with NULL or Blank! " +
                            "Title: \"${article[EnumArticle.TITLE]}\" " +
                            "Content: \"${article[EnumArticle.CONTENT]} " +
                            "Link: \"${article[EnumArticle.LINK]}\" " +
                            "Flag: \"${article[EnumArticle.FLAG]}\" \n \n"
                )
            }
        }
    }

    /**
     * Save the new Article
     *
     * @param article as HashMap with EnumArticles<String>
     **/
    private fun setArticle(
        article: HashMap<EnumArticle, String>
    ) {
        ArticleInsertDB(context).insertArticle(article)
    }

    /**
     * Set every Article to false.
     *
     * @param flag as Boolean to set all Articles Flag with this
     **/
    private fun setAllArticlesFlags(
        flag: Boolean
    ) {
        ArticleUpdateDB(context).setAllArticleFlags(flag)
    }

    /**
     * Find Article in the SQL-Database as Boolean.
     * Search with Title and Link.
     *
     * @param article as HashMap<EnumArticle, String>
     * @return Boolean that find a Query
     **/
    private fun getQueryArticle(
        article: HashMap<EnumArticle, String>
    ): Boolean {
        return ArticleGetDB(context).queryData(article)
    }

    /**
     * Get all saved Article from SQL-Database as HashMap<String>
     *
     * @return Array with HashMap with EnumArticles<String>
     **/
    private fun getArticles(): ArrayList<HashMap<EnumArticle, String>> {
        return ArticleGetDB(context).readData()
    }

    /**
     * Get one saved flagged Article from SQL-Database as HashMap<String>
     *
     * @param flag as Boolean for the Articles with this Flag
     * @return Array with HashMap with EnumArticles<String>
     **/
    private fun getFlaggedArticles(
        flag: Boolean
    ): ArrayList<HashMap<EnumArticle, String>> {
        return ArticleGetDB(context).queryFlaggedArticles(flag)
    }

    /**
     * Delete all Articles from SQL-Database
     **/
    private fun deleteFlaggedArticles(
        flag: Boolean
    ) {
        ArticleDeleteDB(context).deleteFlaggedArticles(flag)
    }

    /**
     * Delete duplicates in SQL-Database.
     **/
    private fun deleteDuplicates() {
        ArticleDeleteDB(context).deleteDuplicatesInSQL()
    }

    /**
     * get the new Article from SQL.
     *
     * return as HashMap with EnumArticles as String
     **/
    fun getNotification(): HashMap<EnumArticle, String> {
        val returnHashMap: HashMap<EnumArticle, String> = HashMap<EnumArticle, String>()
        val array: ArrayList<HashMap<EnumArticle, String>> = getFlaggedArticles(true)

        for (i in 0 until array.size) {
            val hashMap = array[i]

            if (hashMap[EnumArticle.FLAG].toBoolean()) {
                returnHashMap[EnumArticle.TITLE] = hashMap[EnumArticle.TITLE].toString()
                returnHashMap[EnumArticle.CONTENT] = hashMap[EnumArticle.CONTENT].toString()
                returnHashMap[EnumArticle.LINK] = hashMap[EnumArticle.LINK].toString()
                returnHashMap[EnumArticle.FLAG] = hashMap[EnumArticle.FLAG].toString()

                // Delete all old Articles with the Flag false after that set the new Articles to the Flag false
                deleteFlaggedArticles(false)
                setAllArticlesFlags(false)
                break
            }
        }
        return returnHashMap
    }

    /**
     * Load new Article from Server
     **/
    fun loadArticlesFromServer(
    ) {
        LoadArticlesFromServer(context).loadArticles()
    }

    /**
     * Delete the hole Article SQL-Database
     */
    fun deleteDatabase(
    ){
        ArticleDeleteDB(context).deleteSQL()
    }
}