package de.linkelisteortenau.app.ui.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-11
 *
 * Events Fragment for the App
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.backend.notification.EnumNotificationBundle
import de.linkelisteortenau.app.databinding.RecyclerViewBinding
import de.linkelisteortenau.app.ui.events.list_view.*

/**
 * Class for the Events Fragment Recycler
 **/
class EventsFragment : Fragment() {
    private var _binding: RecyclerViewBinding? = null
    private val binding get() = _binding!!

    //private val newEventsActivityRequestCode = 1
    private val contentListViewModel by viewModels<EventsListViewModel> {
        EventsListViewModelFactory(requireContext())
    }

    /**
     * Lifecycle
     *
     * Fragment lifecycle create
     * with inflate transition
     * @see <a href="https://developer.android.com/guide/fragments/lifecycle">Fragment Lifecycle</a>
     * @see <a href="https://developer.android.com/guide/fragments/animate">Fragment Animate</a>
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val inflater = TransitionInflater.from(requireContext())
        //enterTransition = inflater.inflateTransition(R.transition.fade)
        //exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    /**
     * Lifecycle
     *
     * Fragment lifecycle create view
     * @see <a href="https://developer.android.com/guide/fragments/lifecycle">Fragment Lifecycle</a>
     **/
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Instantiates recyclerEventHeaderAdapter and recyclerEventsContentAdapter.
        // Both adapters are added to concatEventAdapter. Which displays the contents sequentially
        val recyclerEventHeaderAdapter = RecyclerEventHeaderAdapter()
        val recyclerEventContentAdapter = RecyclerEventContentAdapter { content -> adapterEventOnClick(content) }
        val concatEventAdapter = ConcatAdapter(recyclerEventHeaderAdapter, recyclerEventContentAdapter)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = concatEventAdapter

        contentListViewModel.eventsLiveData.observe(viewLifecycleOwner) { it ->
            it?.let {
                recyclerEventContentAdapter.submitList(it as MutableList<DataEvents>)
                recyclerEventHeaderAdapter.updateEventCount(it.size)
            }
        }
        return root
    }

    /**
     * Opens EventContentFragment when RecyclerView item is clicked.
     *
     * @param event as DataEvents
     */
    private fun adapterEventOnClick(
        event: DataEvents
    ) {
        val extra = Bundle()
        extra.putString(EnumNotificationBundle.LINK.string, event.eventLink)

        val navController = findNavController()
        navController.navigate(R.id.nav_event_content, extra)
    }

    /**
     * Lifecycles
     *
     * Fragment lifecycle resume
     * @see <a href="https://developer.android.com/guide/fragments/lifecycle">Fragment Lifecycle</a>
     **/
    override fun onResume() {
        super.onResume()
    }

    /**
     * Lifecycles
     *
     * Fragment lifecycle destroy
     * @see <a href="https://developer.android.com/guide/fragments/lifecycle">Fragment Lifecycle</a>
     **/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
