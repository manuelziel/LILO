package de.linkelisteortenau.app.ui.events.list_view

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Show Events Content
 **/
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.backend.events.DataEvents

/**
 * Class to show Data Events
 *
 * @param onClick show Data Events as Unit
 **/
class RecyclerEventContentAdapter(private val onClick: (DataEvents) -> Unit) :
    ListAdapter<DataEvents, RecyclerEventContentAdapter.EventViewHolder>(EventDiffCallback) {

    /**
     * ViewHolder for Event, takes in the inflated view and the onClick behavior.
     *
     * @param itemView for binding items
     * @param onClick for click action
     **/
    class EventViewHolder(
        itemView: View,
        val onClick: (DataEvents) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val eventTextTitle: TextView = itemView.findViewById(R.id.event_title)
        private val eventTextWeekday: TextView = itemView.findViewById(R.id.event_weekday)
        private val eventTextDay: TextView = itemView.findViewById(R.id.event_date)
        private val eventTextStartTime: TextView = itemView.findViewById(R.id.event_start_time)
        private val eventTextEndTime:TextView = itemView.findViewById(R.id.event_end_time)

        private var currentEvent: DataEvents? = null

        init {
            itemView.setOnClickListener {
                currentEvent?.let {
                    onClick(it)
                }
            }
        }

        /**
         * Bind event name and other stuff to content layout.
         *
         * @param event
         **/
        fun bind(
            event: DataEvents
        ) {
            currentEvent = event

            eventTextTitle.text = event.title
            eventTextWeekday.text = event.weekday
            eventTextDay.text = event.date
            //eventTextStartText.text = context.getString(R.string.event_recycler_event_start_text)
            eventTextStartTime.text = event.start
            //eventTextEndText.text = context.getString(R.string.event_recycler_event_end_text)
            eventTextEndTime.text = event.end
        }
    }

    /**
     * Creates and inflates view and return EventViewHolder.
     *
     * @param parent is the View Group
     * @param viewType as Intager
     **/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_event_item, parent, false)
        return EventViewHolder(view, onClick)
    }

    /**
     * Gets current event and uses it to bind view.
     *
     * @param holder as Event View Holder
     * @param position as Intager
     **/
    override fun onBindViewHolder(
        holder: EventViewHolder,
        position: Int
    ) {
        val event = getItem(position)
        holder.bind(event)
    }
}

/**
 * Callback for the Items
 **/
object EventDiffCallback : DiffUtil.ItemCallback<DataEvents>() {
    override fun areItemsTheSame(
        oldItem: DataEvents,
        newItem: DataEvents
    ): Boolean {
        return oldItem == newItem
    }

    /**
     * Check the Content
     **/
    override fun areContentsTheSame(
        oldItem: DataEvents,
        newItem: DataEvents
    ): Boolean {
        return oldItem.id == newItem.id
    }
}