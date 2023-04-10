package de.linkelisteortenau.app.ui.home

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Home Fragment of the Website
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import de.linkelisteortenau.app.HOST_URL_ORGANISATION
import de.linkelisteortenau.app.WEB_VIEW_HTTP_SCHEM
import de.linkelisteortenau.app.backend.notification.EnumNotificationBundle
import de.linkelisteortenau.app.databinding.FragmentWebViewBinding
import de.linkelisteortenau.app.ui.WebViewController

/**
 * Class for Content
 **/
class HomeFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var link: String
    private lateinit var parentLink: String

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

        // Load the Arguments(Notification ect...) from MainActivity and create two Links
        // One for the Link to open and one for the Parent Category when Back are pressed
        val arg = arguments?.containsKey(EnumNotificationBundle.LINK.string)
        if (arg == true) {
            link = requireArguments().getString(EnumNotificationBundle.LINK.string).toString()
            parentLink = (WEB_VIEW_HTTP_SCHEM + HOST_URL_ORGANISATION)
            arguments?.clear()
        } else {
            link = (WEB_VIEW_HTTP_SCHEM + HOST_URL_ORGANISATION)
            parentLink = (WEB_VIEW_HTTP_SCHEM + HOST_URL_ORGANISATION)
        }

        binding.progressBarWebView.isVisible = true
        binding.textViewProgressBar.isVisible = true
        binding.textViewWebView404Text.isVisible = false
        binding.webView.isVisible = false

        // View web content
        val requireActivity = requireActivity()

        context?.let { WebViewController(it).run(requireActivity, binding, link, parentLink = parentLink) }

        return root
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
