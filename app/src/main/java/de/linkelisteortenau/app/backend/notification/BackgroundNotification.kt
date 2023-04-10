package de.linkelisteortenau.app.backend.notification

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Notification for the APP in Android.
 **/
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.linkelisteortenau.app.MainActivity
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.backend.articles.Articles
import de.linkelisteortenau.app.backend.articles.EnumArticle
import de.linkelisteortenau.app.backend.connection.Connection
import de.linkelisteortenau.app.backend.debug.DEBUG_BACKGROUND_WORKER
import de.linkelisteortenau.app.backend.events.EnumEventNotification
import de.linkelisteortenau.app.backend.events.Events
import de.linkelisteortenau.app.backend.preferences.Preferences

/**
 * Global Enum for Notification Bundle.
 *
 * @param string as String
 **/
enum class EnumNotificationBundle(val string: String) {
    TYP("content_typ"), LINK("content_link"),
}

/**
 * Global Enum for Notification Bundle Typ.
 *
 * @param string as String
 **/
enum class EnumNotificationTyp(val string: String) {
    EVENT("typ_event"), ARTICLE("typ_article")
}

/**
 * Class for background Notification for showing Events or new Articles.
 *
 * @see <a href="https://developer.android.com/develop/ui/views/notifications/build-notification">Notifications</a>
 * @param context as Context
 **/
class BackgroundNotification(
    val context: Context
) {
    val debug = Preferences(context).getSystemDebug()
    private val channelId = "LiLO"
    private val channelName = "LiLO Push-Notification"
    private val channelDescription = "LiLO NOTIFICATION"
    private val channelGroupKeyEvent = "de.linkelisteortenau.app.GROUP_KEY_EVENT"
    private val channelGroupKeyArticle = "de.linkelisteortenau.app.GROUP_KEY_ARTICLE"
    private val channelGroupKeySummary = "de.linkelisteortenau.app.GROUP_KEY_SUMMARY"

    private lateinit var newEventNotification: Notification
    private lateinit var newArticleNotification: Notification

    /**
     * Loads new events and articles and creates push notifications if the user has enabled them.
     **/
    fun newNotifications() {
        // Create a notification channel.
        createNotificationChannel()

        val connection = Connection(context)
        val preferences = Preferences(context)
        val events = Events(context)
        val articles = Articles(context)

        // Check if the user has enabled push notifications and if the server connection is available.
        if (connection.connectionToServer() && preferences.getUserPrivacyPolicy()) {
            val eventNotificationData = events.getNotification()
            val articleNotificationData = articles.getNotification()

            if (debug) {
                Log.d(DEBUG_BACKGROUND_WORKER, "Push notification preference - events: ${preferences.getUserShowEventsNotification()}, articles: ${preferences.getUserShowArticlesNotification()}")
            }

            // Create event notification if enabled.
            when {
                eventNotificationData[EnumEventNotification.FLAG].toBoolean() && preferences.getUserShowEventsNotification() -> {
                    newEventNotification(eventNotificationData)
                }
            }

            // Create article notification if enabled.
            when {
                articleNotificationData[EnumArticle.FLAG].toBoolean() && preferences.getUserShowArticlesNotification() -> {
                    newArticleNotification(articleNotificationData)
                }
            }

            // Notify the notification manager.
            notificationManager(eventNotificationData, articleNotificationData, preferences.getUserShowEventsNotification(), preferences.getUserShowArticlesNotification())
        }
    }

    /**
     * Function to for Events and Articles in an Group.
     * This Group shows all Content as Summary.
     **/
    private fun summary(): Notification {
        return NotificationCompat.Builder(context, channelId).setContentTitle("Summary")
            // Set content text to support devices running API level < 24.
            //.setContentText("Two new messages")
            .setSmallIcon(R.drawable.lilo_notification)
            // Build summary info into InboxStyle template.
            .setStyle(
                NotificationCompat.InboxStyle()
                //.addLine("LiLO Check this out")
                //.addLine("Mr. Scherer launch the Party")
                //.setBigContentTitle("2 new messages from Mr. Scherer")
                //.setSummaryText("info@linkelisteortenau.de")
            )
            // Specify which group this notification belongs to.
            .setGroup(channelGroupKeySummary)
            // Set this notification as the summary for the group.
            .setGroupSummary(true).build()
    }

    /**
     * Function for the Event Push-Notification.
     *
     * @param event as HashMap<EnumEventNotification, String>
     **/
    private fun newEventNotification(
        event: HashMap<EnumEventNotification, String>
    ) {
        // The Link for opening the Organisation contribution-page
        //val link = "https://www.linke-liste-ortenau.de/kontakt/"

        // Converting the .png Image file to a Bitmap!
        //val imgBitmap = BitmapFactory.decodeResource(resources, R.drawable.gfg_green)

        // Making intent to open the Organisation Notification Content
        //val contentIntent = organisationOpenerIntent(eventLink)
        val contentIntent = organisationOpenerIntent(EnumNotificationTyp.EVENT.toString(), event[EnumEventNotification.LINK].toString())

        // Making intent2 to open The Organisation about page
        //val intent2 = organisationOpenerIntent(link2)

        // Making pendingIntent to open the Organisation Notification Content
        // page after clicking the Notification
        val pendingContentIntent = PendingIntent.getActivity(
            context, 5, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Making pendingIntent2 to open the Organisation about
        // page after clicking the actionButton of the notification
        //val pendingIntent2 = PendingIntent.getActivity(context, 6, intent2, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // By invoking the notificationChannel() function we're registering our channel to the System
        //notificationChannel(context, channelId, channelName, channelDescription)

        newEventNotification = NotificationCompat.Builder(context, channelId)
            // adding notification Title
            .setContentTitle(
                "${context.getString(R.string.notification_event_title)} ${event[EnumEventNotification.HOUR]}:${event[EnumEventNotification.MINUTE]} ${
                    context.getString(
                        R.string.notification_title_time
                    )
                }"
            )

            // adding notification Text
            .setContentText(event[EnumEventNotification.TITLE])

            // adding notification SmallIcon
            .setSmallIcon(R.drawable.lilo_notification)

            // adding notification Priority
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // making the notification clickable
            .setContentIntent(pendingContentIntent).setAutoCancel(true)

            // only one Alert
            .setOnlyAlertOnce(true)

            // CATEGORY_ALARM, CATEGORY_REMINDER, CATEGORY_EVENT, or CATEGORY_CALL
            .setCategory(NotificationCompat.CATEGORY_EVENT)

            // Specify which group this notification belongs to.
            .setGroup(channelGroupKeyEvent)

            // Set this notification as the summary for the group.
            .setGroupSummary(true)

            // adding action button
            //.addAction(0, "LET CONTRIBUTE", pendingIntent2)

            // adding largeIcon
            //.setLargeIcon(imgBitmap)

            // making notification Expandable
            //.setStyle(NotificationCompat.BigPictureStyle()
            //.bigPicture(imgBitmap)
            //.bigLargeIcon(null))
            .build()
    }

    /**
     * Function for the Article Push-Notification.
     *
     * @param article as HashMap<EnumArticles, String>
     **/
    private fun newArticleNotification(
        article: HashMap<EnumArticle, String>
    ) {
        // The Link for opening the Organisation contribution-page
        //val link = "https://www.linke-liste-ortenau.de/kontakt/"

        // Converting the .png Image file to a Bitmap!
        //val imgBitmap = BitmapFactory.decodeResource(resources, R.drawable.gfg_green)

        // Making intent to open the Organisation Notification Content
        //val contentIntent = OrganisationOpenerIntent(eventLink)
        val contentIntent = organisationOpenerIntent(EnumNotificationTyp.ARTICLE.toString(), article[EnumArticle.LINK].toString())

        // Making intent2 to open The Organisation about page
        //val intent2 = organisationOpenerIntent(link2)

        // Making pendingIntent to open the Organisation Notification Content
        // page after clicking the Notification
        val pendingContentIntent = PendingIntent.getActivity(
            context, 5, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Making pendingIntent2 to open the Organisation about
        // page after clicking the actionButton of the notification
        //val pendingIntent2 = PendingIntent.getActivity(context, 6, intent2, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // By invoking the notificationChannel() function we're registering our channel to the System
        //notificationChannel(context, channelId, channelName, channelDescription)

        // Building the notification
        newArticleNotification = NotificationCompat.Builder(context, channelId)

            // adding notification Title
            //.setContentTitle(articlesAsHashMap[EnumArticle.TITLE])
            //.setContentTitle(context.getString(R.string.notification_article_new_title))

            // adding notification Text
            //.setContentText(articlesAsHashMap[EnumArticle.CONTENT])
            .setContentText(article[EnumArticle.TITLE])

            // adding notification SmallIcon
            .setSmallIcon(R.drawable.lilo_notification)

            // adding notification Priority
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // making the notification clickable
            .setContentIntent(pendingContentIntent).setAutoCancel(true)

            // only one Alert
            .setOnlyAlertOnce(true)

            // CATEGORY_ALARM, CATEGORY_REMINDER, CATEGORY_EVENT, or CATEGORY_CALL
            //.setCategory(NotificationCompat.CATEGORY_EVENT)

            // Specify which group this notification belongs to.
            .setGroup(channelGroupKeyArticle)

            // Set this notification as the summary for the group.
            .setGroupSummary(true)

            // adding action button
            //.addAction(0, "LET CONTRIBUTE", pendingIntent2)

            // adding largeIcon
            //.setLargeIcon(imgBitmap)

            // making notification Expandable
            //.setStyle(NotificationCompat.BigPictureStyle()
            //.bigPicture(imgBitmap)
            //.bigLargeIcon(null))
            .build()
    }

    /**
     * Creates a notification channel.
     */
    private fun createNotificationChannel() {
        // Create the channel
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
        }
        // Registering the channel to the System
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private var cnt = 1

    /**
     * Push all Notifications in a Group
     */
    private fun notificationManager(
        event: HashMap<EnumEventNotification, String>, article: HashMap<EnumArticle, String>, userShowEventsNotification: Boolean, userShowArticlesNotification: Boolean
    ) {
        //val summary = 0

        NotificationManagerCompat.from(context).apply {
            if ((event[EnumEventNotification.FLAG].toBoolean()) && userShowEventsNotification) {
                notify(cnt, newEventNotification)
                cnt++
            }

            if ((article[EnumArticle.FLAG].toBoolean()) && userShowArticlesNotification) {
                notify(cnt, newArticleNotification)
            }
            //notify(summary, summary(context))
        }
    }

    /**
     * The function Organisation-OpenerIntent() returns
     * an Implicit Intent to open a webpage
     *
     * @param link to open in the event content Fragment
     **/
    private fun organisationOpenerIntent(
        typ: String, link: String
    ): Intent {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(EnumNotificationBundle.TYP.string, typ)
        intent.putExtra(EnumNotificationBundle.LINK.string, link)

        return intent
    }
}

