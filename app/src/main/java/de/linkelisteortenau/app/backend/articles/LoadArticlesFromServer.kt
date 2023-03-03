package de.linkelisteortenau.app.backend.articles

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Classes for Articles to load and save
 **/
import android.content.Context
import android.util.Log
import de.linkelisteortenau.app.GLOBAL_NULL
import de.linkelisteortenau.app.HOST_URL_ORGANISATION
import de.linkelisteortenau.app.WEB_VIEW_HTTP_SCHEM
import de.linkelisteortenau.app.backend.debug.DEBUG_LOAD_ARTICLE
import de.linkelisteortenau.app.backend.preferences.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

/**
 * Class to load Articles from the Web Server
 *
 * @param context as Context
 **/
class LoadArticlesFromServer (val context: Context){
    val debug = Preferences(context).getSystemDebug()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    /**
     * Load new Articles in an Kotlin Coroutine Scope and Supervisor Job.
     *
     * Launched in the main thread, Every coroutine builder (like launch, async, and others)
     * and every scoping function (like coroutineScope and withContext) provides its own
     * scope with its own Job instance into the inner block of code it runs.
     * By convention, they all wait for all the coroutines inside their block to complete
     * before completing themselves, thus enforcing the structured concurrency.
     * See Job documentation for more details.
     *
     * Creates a supervisor job object in an active state.
     *
     *
     * @see <a href="https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html">SupervisorJob</a>
     * @see <a href="https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/">CoroutineScope</a>
     * **/
    fun loadArticles() {
        scope.launch {
        loadArticlesFunction()
        }
    }

    /**
     * Load new Articles with Jsoup lib from https://www.Linke-Liste-Ortenau.de/
     * Session load timeout are disabled!
     *
     * The Content are in every h2 Element
     *
     * Load the elements from first Article. Every element are stored in div.entry-container header h1
     * @see <a href="https://www.linke-liste-ortenau.de/">Home Page</a>
     **/
    private val articleContent = "null" // No Content implemented
    private fun loadArticlesFunction() {
        try {
            val doc: Document = Jsoup.connect("$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION").timeout(0).get()

            if (doc.hasText()) {
                val hashMap: HashMap<EnumArticle, String> = HashMap<EnumArticle, String>()
                val eContent: Elements = doc.select("div.entry-container h2")
                val eHeader: MutableList<String> = eContent.select("h2").eachText()
                val eLink: MutableList<String> = eContent.select("a[href]").eachAttr("href")

                for (i in 0 until eHeader.size){
                    hashMap[EnumArticle.TITLE]  = eHeader[i]
                    hashMap[EnumArticle.CONTENT]= GLOBAL_NULL
                    hashMap[EnumArticle.LINK]   = eLink[i]
                    hashMap[EnumArticle.FLAG]   = true.toString()

                    if (debug){
                        Log.d(DEBUG_LOAD_ARTICLE, "Incoming Article: ${eHeader[i]} with link ${eLink[i]}")
                    }

                    Articles(context).saveArticles(hashMap)
                }
            }
        } catch (e: IOException) {
            Log.e(DEBUG_LOAD_ARTICLE, e.message.toString())
        }
    }
}