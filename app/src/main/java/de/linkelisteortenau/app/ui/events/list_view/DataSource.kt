package de.linkelisteortenau.app.ui.events.list_view

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Events Fragment for the App
 **/
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.linkelisteortenau.app.backend.events.Events

/**
 * Class for the events live data
 *
 * Handles operations on eventsLiveData and holds details about it.
 * @param context as Context
 **/
class DataSource(context: Context) {
    private val initialEventsList = Events(context).getEventsAsDataList()
    private val eventsLiveData = MutableLiveData(initialEventsList)

    /**
     * Adds events to liveData and posts value.
     *
     * @param event as data class DataEvents
     */
    fun addEvent(
        event: DataEvents
    ) {
        val currentList = eventsLiveData.value
        if (currentList == null) {
            eventsLiveData.postValue(listOf(event))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, event)
            eventsLiveData.postValue(updatedList)
        }
    }

    /**
     * Removes events from liveData and posts value.
     *
     * @param event as data class DataEvents
     */
    fun removeEvent(
        event: DataEvents
    ) {
        val currentList = eventsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(event)
            eventsLiveData.postValue(updatedList)
        }
    }

    /**
     * Returns event given an ID.
     *
     * @param id is the Event ID
     * @return DataEvents
     */
    fun getEventForId(
        id: Long
    ): DataEvents? {
        eventsLiveData.value?.let { event ->
            return event.firstOrNull{ it.id == id}
        }
        return null
    }

    /**
     * Returns event List as LiveData .
     *
     * @return LiveData
     */
    fun getEventList(): LiveData<List<DataEvents>> {
        return eventsLiveData
    }

    /**
     * Returns a random event asset for events that are added.
     *
     * @return Intager
     */
    fun getRandomEventImageAsset(): Int {
        val randomNumber = (initialEventsList.indices).random()
        return initialEventsList[randomNumber].eventStart.toInt()
    }



    companion object {
        private var INSTANCE: DataSource? = null

        /**
         * Returns a random event asset for events that are added.
         *
         * @param context as Context
         */
        fun getDataSource(context: Context): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}