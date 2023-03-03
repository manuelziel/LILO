package de.linkelisteortenau.app.backend.debug

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Classes and Constants for the Debug
 **/

/**
 * Constants for the Debug
 **/
const val DEBUG = "DEBUG"
const val DEBUG_INTENT_SERVICE = "Intent Service"
const val DEBUG_ON_CREATE = " -> onCreate"
const val DEBUG_ON_START_COMMAND = " -> onStartCommand"
const val DEBUG_START_SERVICE = " -> startService"
const val DEBUG_FOREGROUND_SERVICE = " -> startForegroundService"
const val DEBUG_BIND = " -> onBind"
const val DEBUG_UNBIND = " -> unBind"
const val DEBUG_REBIND = " -> reBind"
const val DEBUG_DESTROY = " -> destroy"

/** BACKGROUND Service **/
const val DEBUG_BACKGROUND_BROADCAST_RECEIVER = "LiLO_Backend_Broadcast_Receiver"
const val DEBUG_BACKGROUND_SERVICE = "LiLO_Backend_Service"
const val DEBUG_BACKGROUND_LOOP_ARTICLES = " -> run Articles"
const val DEBUG_BACKGROUND_LOOP_EVENTS = " -> run Events"
const val DEBUG_BACKGROUND_LOOP_NOTIFICATION = " -> run Notification"

/** Permission Notification **/
const val DEBUG_PERMISSION_NOTIFICATION             = "LiLO_Permission_Notification"
const val DEBUG_PERMISSION_NOTIFICATION_TRUE        = "Permission for Notification has been granted"
const val DEBUG_PERMISSION_NOTIFICATION_FALSE       = "Permission for Notification hasn't been granted"

/** Notification **/
const val DEBUG_EVENT_LOAD_TEST_TITLE               = "DEBUG EVENT"
const val DEBUG_EVENT_LOAD_TEST_LINK                = "https://www.linke-liste-ortenau.de/events/event/test/"

/** Time **/
const val DEBUG_TIME                                = "LiLO_Time"

/** WebView **/
const val DEBUG_WEB_VIEW_ERROR                      = "LiLO_WebView"

/** Event **/
const val DEBUG_EVENT                               = "LiLO_Event"
const val DEBUG_EVENT_ALREADY_SAVED                 = "Event already saved: "
const val DEBUG_EVENT_IN_PASTE                      = "Event is in the past and will not be saved"
const val DEBUG_EVENT_CHECK_NOT_VALIDATED           = "Event is not validated"

/** Article **/
const val DEBUG_ARTICLE                             = "LiLO_Article"

/** Connection **/
const val DEBUG_CONNECTION                          = "LiLO_Connection"
const val DEBUG_CONNECTION_CELLULAR_HAS_TRANSPORT   = "NetworkCapabilities has TRANSPORT_CELLULAR"
const val DEBUG_CONNECTION_WIFI_HAS_TRANSPORT       = "NetworkCapabilities has TRANSPORT_WIFI"
const val DEBUG_CONNECTION_ETHERNET_HAS_TRANSPORT   = "NetworkCapabilities has TRANSPORT_ETHERNET"
const val DEBUG_CONNECTION_HAS_NO_TRANSPORT         = "NetworkCapabilities TRANSPORT FALSE"

/** Load Event From Server **/
const val DEBUG_LOAD_EVENT = "LiLO_Load_Event"

/** Load Article From Server **/
const val DEBUG_LOAD_ARTICLE = "LiLO_Load_Article"

/** SQL Event **/
const val DEBUG_SQL_EVENT_HELPER                    = "LiLO_DB_Event_Helper"
const val DEBUG_SQL_EVENT_HELPER_CREATE_DB          = "Create Event SQL Database"
const val DEBUG_SQL_EVENT_HELPER_UPGRADE            = "Upgrade Event SQL Database"

const val DEBUG_SQL_EVENT_DELETE                    = "LiLO_DB_Event_Delete"
const val DEBUG_SQL_EVENT_DELETE_FAILED             = "Delete Events there end are in the past failed"
const val DEBUG_SQL_EVENT_DELETE_SUCCESS            = "Delete Events there end are in the past success"
const val DEBUG_SQL_EVENT_DELETE_DUPLICATES_FAILED  = "Delete duplicates failed"
const val DEBUG_SQL_EVENT_DELETE_DUPLICATES_SUCCESS = "Delete duplicates success"

const val DEBUG_SQL_EVENT_UPDATE                    = "LiLO_DB_Event_Update"
const val DEBUG_SQL_EVENT_UPDATE_ALL_FAILED         = "Change all Events failed with flagged: "
const val DEBUG_SQL_EVENT_UPDATE_ALL_SUCCESS        = "Change all Events success with new flag: "
const val DEBUG_SQL_EVENT_UPDATE_ONE_FAILED         = "Change Event flag failed with:"
const val DEBUG_SQL_EVENT_UPDATE_ONE_SUCCESS        = "Change Event flag success with:"

const val DEBUG_SQL_EVENT_GET                       = "LiLO_DB_Event_Get"
const val DEBUG_SQL_EVENT_GET_EMPTY                 = "Event DB is empty"

const val DEBUG_SQL_EVENT_INSERT                    = "LiLO_DB_Event_Insert"

/** SQL Article **/
const val DEBUG_SQL_ARTICLE_HELPER                  = "LiLO_DB_Article_Helper"
const val DEBUG_SQL_ARTICLE_HELPER_CREATE_DB        = "Create Article SQL Database"
const val DEBUG_SQL_ARTICLE_HELPER_UPGRADE          = "Upgrade Article SQL Database"

const val DEBUG_SQL_ARTICLE_DELETE                  = "LiLO_DB_Article_Delete"
const val DEBUG_SQL_ARTICLE_DELETE_FAILED           = "Delete Article there end are in the past failed"
const val DEBUG_SQL_ARTICLE_DELETE_SUCCESS          = "Delete Article there end are in the past success"
const val DEBUG_SQL_ARTICLE_DELETE_DUPLICATES_FAILED    = "Delete duplicates failed"
const val DEBUG_SQL_ARTICLE_DELETE_DUPLICATES_SUCCESS   = "Delete duplicates success"

const val DEBUG_SQL_ARTICLE_UPDATE                  = "LiLO_DB_Article_Update"
const val DEBUG_SQL_ARTICLE_UPDATE_ALL_FAILED       = "Change all Articles failed to flag with: "
const val DEBUG_SQL_ARTICLE_UPDATE_ALL_SUCCESS      = "Change all Articles flag success with: "
const val DEBUG_SQL_ARTICLE_UPDATE_ONE_FAILED       = "Change Article flag failed with:"
const val DEBUG_SQL_ARTICLE_UPDATE_ONE_SUCCESS      = "Change Article flag success with:"

const val DEBUG_SQL_ARTICLE_GET                     = "LiLO_DB_Article_Get"
const val DEBUG_SQL_ARTICLE_GET_DB                  = "Get Article from DB: "
const val DEBUG_SQL_ARTICLE_GET_FLAGGED_DB          = "Flagged Article from DB: "
const val DEBUG_SQL_ARTICLE_GET_EMPTY               = "Article DB is empty"

const val DEBUG_SQL_ARTICLE_INSERT                  = "LiLO_DB_Article_Insert"
const val DEBUG_SQL_ARTICLE_INSERT_FAILED           = "Insert Article to DB failed: "
const val DEBUG_SQL_ARTICLE_INSERT_SUCCESS          = "Insert Article to DB success: "

/** Share **/
const val DEBUG_SHARE                               = "LiLO_Share"