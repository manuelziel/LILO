package de.linkelisteortenau.app

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Constants for the Global variable
 **/
const val GLOBAL_NULL   = "NULL"

/**
 * Constants for global things
 *
 * HOST_URL_ORGANISATION is the URL from the server Hostname
 **/
const val HOST_URL_ORGANISATION = "www.linke-liste-ortenau.de"

/**
 * Constants for global Time
 *
 *
 **/
const val TIME_HOUR = 3600000
const val TIME_MINUTE = 60000
const val TIME_PATTER_GLOBAL = "yyyy-MM-dd'T'HH:mm:ssXXX"
const val TIME_PATTER_YEAR = "yyyy"
const val TIME_PATTER_MONTH = "MMM"
const val TIME_PATTER_DAY = "dd"
const val TIME_PATTER_WEEKDAY = "EEEE"
const val TIME_PATTER_HOUR = "HH"
const val TIME_PATTER_MINUTE = "mm"

/**
 * Constants for global Preferences
 *
 * Locale Default is "DE"
 **/
const val PREF_SYSTEM_DEBUG                                 = "pref_system_debug"
const val PREF_SYSTEM_DEBUG_VALUE                           = "false"
const val PREF_USER_PRIVACY_POLICY                          = "pref_user_privacy_policy"
const val PREF_USER_PRIVACY_POLICY_VALUE                    = "false"
const val PREF_LOCALE                                       = "pref_locale"
const val PREF_LOCALE_VALUE                                 = "DE"
const val PREF_USER_SHOW_EVENT_NOTIFICATION                 = "pref_user_show_event_notification"
const val PREF_USER_SHOW_EVENT_NOTIFICATION_VALUE           = "false"
const val PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR   = "pref_user_notification_time_multiplicator"
const val PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR_VALUE = "1"
const val PREF_USER_SHOW_ARTICLE_NOTIFICATION               = "pref_user_show_article_notification"
const val PREF_USER_SHOW_ARTICLE_NOTIFICATION_VALUE         = "false"

/**
 * Constants for user key Preferences.
 * The Keys are only used in the Preference XML
 *
 **/
const val USER_PREF_KEY_RESET                   = "pref_key_user_reset"
const val USER_PREF_KEY_APP_VERSION             = "pref_key_user_app_version"
const val USER_PREF_KEY_FEEDBACK                = "pref_key_user_app_feedback"
const val USER_PREF_KEY_THIRD_PARTY_SOFTWARE    = "pref_key_user_third_pary_software"
const val USER_PREF_KEY_LICENCE                 = "pref_key_user_licence"
const val USER_PREF_KEY_BETA                    = "pref_key_user_beta"
const val USER_PREF_KEY_EVENT_NOTIFICATION      = "pref_key_user_event_notifications"
const val USER_PREF_KEY_ARTICLE_NOTIFICATION    = "pref_key_user_article_notifications"
const val USER_PREF_KEY_DEVELOPER               = "pref_key_user_developer"
const val USER_PREF_KEY_DEBUG                   = "pref_key_user_debug"

/**
 * Constants for Push-Notification
 *
 * Hour (3600000) as millis, Min (60000)
 **/
const val NOTIFICATION_NEXT_UPCOMING_EVENT_TIME = 3600000
const val NOTIFICATION_CHECK_SERVER_EVENT_DELAY_MULTIPLICATOR = 12
const val NOTIFICATION_CHECK_SERVER_EVENT_DELAY = 3600000
const val NOTIFICATION_CHECK_SERVER_ARTICLE_DELAY_MULTIPLICATOR = 1
const val NOTIFICATION_CHECK_SERVER_ARTICLE_DELAY = 3600000
const val NOTIFICATION_CHECK_NOTIFICATION_DELAY_MULTIPLICATOR = 15
const val NOTIFICATION_CHECK_NOTIFICATION_DELAY = 60000

/**
 * Constants for Dialogs TAG
 *
 *
 **/
const val DIALOG_TAG = "RESET"

/**
 * Constants for WebView
 *
 * WEB_VIEW things for the web path
 * JavaScript's to remove Elements from the HTML DOM
 **/
const val WEB_VIEW_HTTP_SCHEM = "https://"
const val WEB_VIEW_URL_SEARCH = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/"
const val WEB_VIEW_URL_EVENTS = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/termine/"
const val WEB_VIEW_URL_ABOUT_ORGANISATION = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/wer-ist-die-lilo/"
const val WEB_VIEW_URL_DEEP_GEOTHERMAL_ENERGY = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/tiefengeothermie/"
const val WEB_VIEW_URL_HEALTH_CARE = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/gesundheitsvorsorge/"
const val WEB_VIEW_URL_HOUSING = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/wohungsbau/"
const val WEB_VIEW_URL_LOCAL_TRANSPORT = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/nahverkehr/"
const val WEB_VIEW_URL_SOCIAL = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/soziales/"
const val WEB_VIEW_URL_TRAINING = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/bildung/"
const val WEB_VIEW_URL_PRIVACY_POLICY = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/datenschutzerklaerung/"

const val WEB_VIEW_JAVASCRIPT_REMOVE_COOKIE_NOTICE = "javascript:(function() { var cookieNotice = document.getElementsByClassName('cookie-notice-container')[0].style.display='none'; })()"
const val WEB_VIEW_JAVASCRIPT_REMOVE_HEADER = "javascript:(function() { var header = document.getElementsByClassName('wrapper')[0].style.display='none'; })()"
const val WEB_VIEW_JAVASCRIPT_REMOVE_HEADER_FEATURED_IMAGE = "javascript:(function() { var headerImage = document.getElementsByClassName('wp-post-image')[0].style.display='none'; })()"
const val WEB_VIEW_JAVASCRIPT_REMOVE_SIDEBAR = "javascript:(function() { var sidebar = document.getElementsByClassName('sidebar sidebar-primary widget-area')[0].style.display='none'; })()"
const val WEB_VIEW_JAVASCRIPT_REMOVE_FOOTER = "javascript:(function() { var footer = document.getElementsByClassName('site-footer')[0].style.display='none'; })()"
// Website has multiple forms! Header, not viewed and last viewed form!
const val WEB_VIEW_JAVASCRIPT_REMOVE_SEARCH = "javascript:(function() { var search = document.forms[2].style.display='none';  })()"

/**
 * Constants for Social Media connect
 *
 *
 **/
const val SIGNAL_GROUP_URL = "https://signal.group/#CjQKINwyW59Pd6Ixd-KX9B3_Zon7cdms_pkv9JcVbzP9AdA1EhDuHcVJOJQCCutlhee5lDKN"
const val TELEGRAM_GROUP_URL = "https://t.me/liloinfogrp"
const val FACEBOOK_GROUP_URL = "https://www.facebook.com/linkelisteortenau"
const val FACEBOOK_PAGE_ID = "linkelisteortenau"