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
 * Constants for global Time.
 *
 * Minute has 60000 millis
 **/
//const val TIME_HOUR = 3600000
const val TIME_MINUTE = 60000
const val TIME_FORMAT_GLOBAL = "yyyy-MM-dd'T'HH:mm:ssXXX"
const val TIME_FORMAT_YEAR = "yyyy"
const val TIME_FORMAT_MONTH = "MMM"
const val TIME_FORMAT_DAY = "dd"
const val TIME_FORMAT_WEEKDAY = "EEEE"
const val TIME_FORMAT_HOUR = "HH"
const val TIME_FORMAT_MINUTE = "mm"

/**
 * Constants for global Preferences
 *
 * Locale Default is "DE"
 **/
const val PREF_SYSTEM_DEBUG                                 = "pref_system_debug"
const val PREF_SYSTEM_DEBUG_VALUE                           = false
const val PREF_USER_PRIVACY_POLICY                          = "pref_user_privacy_policy"
const val PREF_USER_PRIVACY_POLICY_VALUE                    = false
const val PREF_APP_UPDATE                                   = "pref_app_update"
const val PREF_APP_UPDATE_VALUE                             = 0
const val PREF_LOCALE                                       = "pref_locale"
const val PREF_LOCALE_VALUE                                 = "DE"
const val PREF_USER_SHOW_EVENT_NOTIFICATION                 = "pref_user_show_event_notification"
const val PREF_USER_SHOW_EVENT_NOTIFICATION_VALUE           = false
const val PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR   = "pref_user_notification_time_multiplicator"
const val PREF_USER_EVENT_NOTIFICATION_TIME_MULTIPLICATOR_VALUE = 60
const val PREF_USER_SHOW_ARTICLE_NOTIFICATION               = "pref_user_show_article_notification"
const val PREF_USER_SHOW_ARTICLE_NOTIFICATION_VALUE         = false

/**
 * Constants for user key Preferences.
 * The Keys are only used in the Preference XML.
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
 **/
const val NOTIFICATION_EVENT_TIME_RANGE_MIN = 60
const val NOTIFICATION_WORKER_LOOP_TIME_MIN = 30

/**
 * Constants for Dialogs TAG
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
const val WEB_VIEW_URL_EVENTS = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/veranstaltungen/"
//const val WEB_VIEW_URL_DISTRICT_COUNCIL = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/kreistag/"
const val WEB_VIEW_URL_HEALTH_CARE = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/gesundheitsversorgung/"
const val WEB_VIEW_URL_LOCAL_TRANSPORT = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/nahverkehr/"
const val WEB_VIEW_URL_HOUSING = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/wohungsbau/"
const val WEB_VIEW_URL_SOCIAL = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/soziales/"
const val WEB_VIEW_URL_TRAINING = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/bildung/"
const val WEB_VIEW_URL_DEEP_GEOTHERMAL_ENERGY = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/tiefengeothermie/"
const val WEB_VIEW_URL_ABOUT_ORGANISATION = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/wer-ist-die-lilo/"
const val WEB_VIEW_URL_PRIVACY_POLICY = "$WEB_VIEW_HTTP_SCHEM$HOST_URL_ORGANISATION/datenschutzerklaerung/"

const val WEB_VIEW_JAVASCRIPT_REMOVE_COOKIE_NOTICE = "var cookieNotice = document.getElementsByClassName('cookie-notice-container')[0].style.display='none';"
const val WEB_VIEW_JAVASCRIPT_REMOVE_HEADER = "var header = document.getElementsByClassName('site-header')[0].style.display='none';"
const val WEB_VIEW_JAVASCRIPT_REMOVE_HEADER_FEATURED_IMAGE = "var headerImage = document.getElementsByClassName('header-image default-header-image')[0].style.display='none';"
const val WEB_VIEW_JAVASCRIPT_REMOVE_SIDEBAR = "var sidebar = document.getElementsByClassName('sidebar widget-area')[0].style.display='none';"
const val WEB_VIEW_JAVASCRIPT_REMOVE_FOOTER = "var footer = document.getElementsByClassName('footer-wrap')[0].style.display='none';"
// Website has multiple forms! Header, not viewed and last viewed form!
//const val WEB_VIEW_JAVASCRIPT_REMOVE_SEARCH = "var search = document.forms[2].style.display='none';"

//const val WEB_VIEW_JAVASCRIPT_REMOVE_CONTENT = "javascript:(function() { var cookieNotice = document.getElementsByClassName('cookie-notice-container')[0].style.display='none'; var header = document.getElementsByClassName('wrapper')[0].style.display='none'; var headerImage = document.getElementsByClassName('wp-post-image')[0].style.display='none'; var sidebar = document.getElementsByClassName('sidebar sidebar-primary widget-area')[0].style.display='none'; var footer = document.getElementsByClassName('site-footer')[0].style.display='none'; var search = document.forms[2].style.display='none'; "
const val WEB_VIEW_JAVASCRIPT_REMOVE_CONTENT = "$WEB_VIEW_JAVASCRIPT_REMOVE_COOKIE_NOTICE $WEB_VIEW_JAVASCRIPT_REMOVE_HEADER $WEB_VIEW_JAVASCRIPT_REMOVE_HEADER_FEATURED_IMAGE $WEB_VIEW_JAVASCRIPT_REMOVE_SIDEBAR $WEB_VIEW_JAVASCRIPT_REMOVE_FOOTER"

/**
 * Constants for Social Media connect
 **/
const val SIGNAL_GROUP_URL = "https://signal.group/#CjQKINwyW59Pd6Ixd-KX9B3_Zon7cdms_pkv9JcVbzP9AdA1EhDuHcVJOJQCCutlhee5lDKN"
const val TELEGRAM_GROUP_URL = "https://t.me/liloinfogrp"
const val FACEBOOK_GROUP_URL = "https://www.facebook.com/linkelisteortenau"
const val FACEBOOK_PAGE_ID = "linkelisteortenau"
const val INSTAGRAM_PAGE_ID = "lilolinkeliste"
const val YOUTUBE_PAGE_ID = "@liloortenau1138"