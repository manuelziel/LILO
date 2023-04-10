package de.linkelisteortenau.app.ui.events.list_view

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Events List View Model for the EventsFragment
 **/
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.linkelisteortenau.app.backend.events.DataEvents
import de.linkelisteortenau.app.backend.time.EnumTime
import kotlin.random.Random

/**
 * Class Event List View Model
 *
 * @param dataSource class as Variable
 **/
class EventsListViewModel(val dataSource: DataSource) : ViewModel() {
    val eventsLiveData = dataSource.getEventList()

    /**
     * If the name and description are present, create new event and add it to the datasource.
     *
     * @param eventTitle as String from the Event
     * @param eventStartTimeAsHashMap as HashMap with Year, Month, Day, Weekday, Hour and Minute
     * @param eventEndTimeAsHashMap as HashMap with Year, Month, Day, Weekday, Hour and Minute
     **/
    fun insertEvent(
        eventTitle: String?,
        eventStartTimeAsHashMap: HashMap<EnumTime, String>,
        eventEndTimeAsHashMap: HashMap<EnumTime, String>,
        eventLink: String?
    ) {
        if (eventTitle == null || eventLink == null) {
            return
        }

        val eventWeekday = "${eventStartTimeAsHashMap[EnumTime.WEEKDAY]}"
        val eventDate = "${eventStartTimeAsHashMap[EnumTime.DAY]}.${eventStartTimeAsHashMap[EnumTime.MONTH]}"
        val eventStart = "${eventStartTimeAsHashMap[EnumTime.HOUR]}:${eventStartTimeAsHashMap[EnumTime.MINUTE]}"
        val eventEnd = "${eventEndTimeAsHashMap[EnumTime.HOUR]}:${eventEndTimeAsHashMap[EnumTime.MINUTE]}"

        val newEvent = DataEvents(
            Random.nextLong(),
            eventTitle,
            eventWeekday,
            eventDate,
            eventStart,
            eventEnd,
            eventLink
        )
        dataSource.addEvent(newEvent)
    }
}

/**
 * Class Event List View Model Factory
 * View Model Provider
 *
 * @param context as Context
 **/
class EventsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventsListViewModel(
                dataSource = DataSource.getDataSource(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}