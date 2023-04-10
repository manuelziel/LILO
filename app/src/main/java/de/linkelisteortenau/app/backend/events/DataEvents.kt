package de.linkelisteortenau.app.backend.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 *
 * Data class for the Events
 **/
data class DataEvents(
    val id: Long,
    val title: String,
    val weekday: String,
    val date: String,
    val start: String,
    val end: String,
    val link: String
)