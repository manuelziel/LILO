package de.linkelisteortenau.app.backend.sql

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Constants for the Event SQL-Database
 **/
import android.provider.BaseColumns

/**
 * Constants for the Event SQL-Database
 * @author Manuel Ziel
 *
 * EVENTS_TABLE_NAME for the Event SQL-Table
 * ID for the Columns
 *
 * COLUMN_EVENT_TITLE is the Title of the Event.
 * COLUMN_EVENT_START is the Unix timestamp of the start time of the Event.
 * COLUMN_EVENT_END is the Unix timestamp of the end time of the Event.
 * COLUMN_EVENT_LINK is the href https link to the Website of the Event.
 * COLUMN_EVENT_CONTENT is the Text Content of the Event.
 * COLUMN_EVENT_FLAG is the Flag to check the Event whether this is still up to date.
 **/
const val EVENTS_TABLE_NAME = "events_table_lilo"
const val EVENT_ID = BaseColumns._ID

const val COLUMN_EVENT_TITLE = "title";         const val event_title_index: Int = 1
const val COLUMN_EVENT_START = "start";         const val event_start_index: Int = 2
const val COLUMN_EVENT_END = "end";             const val event_end_index: Int = 3
const val COLUMN_EVENT_LINK = "link";           const val event_link_index: Int = 4
const val COLUMN_EVENT_CONTENT = "content";     const val event_content_index: Int = 5
const val COLUMN_EVENT_FLAG = "flag";           const val event_flag_index: Int = 6

