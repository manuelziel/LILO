package de.linkelisteortenau.app.ui.program

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Health Care
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import de.linkelisteortenau.app.WEB_VIEW_URL_HEALTH_CARE
import de.linkelisteortenau.app.databinding.FragmentWebViewBinding
import de.linkelisteortenau.app.ui.WebView

/**
 * Class for Content
 **/
class HealthCareFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
        val url = (WEB_VIEW_URL_HEALTH_CARE)
        context?.let { WebView(it).run(requireActivity, binding, url, parentLink = url) }
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