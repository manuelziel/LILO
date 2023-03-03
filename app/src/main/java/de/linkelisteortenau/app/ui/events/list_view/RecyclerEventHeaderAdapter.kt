package de.linkelisteortenau.app.ui.events.list_view

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * For Event Header
 **/
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.linkelisteortenau.app.R

/**
 * Class to list always displaying one element: the number of events.
 **/
class RecyclerEventHeaderAdapter: RecyclerView.Adapter<RecyclerEventHeaderAdapter.HeaderViewHolder>() {
    private var eventCount: Int = 0

    /**
     * Class ViewHolder for displaying header.
     *
     * @param view as View from layout
     * @return RecyclerView.ViewHolder for the view
     **/
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val eventNumberTextView: TextView = itemView.findViewById(R.id.event_number_text)

        fun bind(eventCount: Int) {
            eventNumberTextView.text = eventCount.toString()
        }
    }

    /**
     * Inflates view and returns HeaderViewHolder.
     *
     * @param parent as ViewGroup from layout
     * @param viewType as Type
     * @return HeaderViewHolder
     **/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_header_item, parent, false)
        return HeaderViewHolder(view)
    }

    /**
     * Binds number of events to the header.
     *
     * @param holder is the event count in the view
     **/
    override fun onBindViewHolder(
        holder: HeaderViewHolder,
        position: Int
    ) {
        holder.bind(eventCount)
    }

    /**
     * Returns number of items, since there is only one item in the header return one.
     *
     * @return int
     **/
    override fun getItemCount(): Int {
        return 1
    }

    /**
     * Updates header to display number of events when a event is added or subtracted.
     *
     * @param updatedEventCount new count integer
     **/
    fun updateEventCount(
        updatedEventCount: Int
    ) {
        eventCount = updatedEventCount
        notifyDataSetChanged()
    }
}