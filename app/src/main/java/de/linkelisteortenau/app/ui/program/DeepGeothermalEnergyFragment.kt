package de.linkelisteortenau.app.ui.program

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12
 *
 * Deep Geothermal Energy
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import de.linkelisteortenau.app.WEB_VIEW_URL_DEEP_GEOTHERMAL_ENERGY
import de.linkelisteortenau.app.databinding.FragmentWebViewBinding
import de.linkelisteortenau.app.ui.WebViewController

/**
 * Class for Content
 **/
class DeepGeothermalEnergyFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

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

        // View web content
        val requireActivity = requireActivity()
        val url = (WEB_VIEW_URL_DEEP_GEOTHERMAL_ENERGY)
        context?.let { WebViewController(it).run(requireActivity, binding, url, parentLink = url) }

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
