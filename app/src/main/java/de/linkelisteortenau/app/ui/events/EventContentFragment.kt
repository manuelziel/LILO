package de.linkelisteortenau.app.ui.events

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Show the the Event Content
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import de.linkelisteortenau.app.HOST_URL_ORGANISATION
import de.linkelisteortenau.app.R
import de.linkelisteortenau.app.WEB_VIEW_HTTP_SCHEM
import de.linkelisteortenau.app.backend.notification.EnumNotificationBundle
import de.linkelisteortenau.app.databinding.FragmentWebViewBinding
import de.linkelisteortenau.app.ui.WebView

/**
 * Class for Event Content
 **/
class EventContentFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var link: String
    private lateinit var parentLink: String

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
        // This callback will only be called when MyFragment is at least Started.
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
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.notification_event_show_content_action_bar_title)


        // Load the Arguments(Notification ect...) from MainActivity and create two Links
        // One for the Link to open and one for the Parent Category when Back are pressed
        val arg = arguments?.containsKey(EnumNotificationBundle.LINK.string)
        if (arg == true) {
            link = requireArguments().getString(EnumNotificationBundle.LINK.string).toString()
            parentLink = link
        } else {
            link = (WEB_VIEW_HTTP_SCHEM + HOST_URL_ORGANISATION)
            parentLink = (WEB_VIEW_HTTP_SCHEM + HOST_URL_ORGANISATION)
        }

        binding.progressBarWebView.isVisible = true
        binding.textViewProgressBar.isVisible = true
        binding.textViewWebView404Text.isVisible = false
        binding.webView.isVisible = false

        return root
    }

    /**
     * Lifecycles
     *
     * Fragment lifecycle start
     * @see <a href="https://developer.android.com/guide/fragments/lifecycle">Fragment Lifecycle</a>
     **/
    override fun onStart() {
        super.onStart()

        // View web content
        val requireActivity = requireActivity()

        context?.let { WebView(it).run(requireActivity, binding, link, parentLink = parentLink) }
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