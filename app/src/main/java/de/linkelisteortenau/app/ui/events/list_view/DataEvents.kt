package de.linkelisteortenau.app.ui.events.list_view

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 *
 * Data class for the event list view recycler
 **/
data class DataEvents(
    val id: Long,
    val eventTitle: String,
    val eventWeekday: String,
    val eventDate: String,
    val eventStart: String,
    val eventEnd: String,
    val eventLink: String
)