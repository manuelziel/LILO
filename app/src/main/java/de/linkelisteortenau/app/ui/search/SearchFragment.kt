package de.linkelisteortenau.app.ui.search

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-01
 *
 * Search
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import de.linkelisteortenau.app.WEB_VIEW_URL_SEARCH
import de.linkelisteortenau.app.databinding.FragmentSearchBinding

/**
 * Class for Search and the Content
 **/
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.editTextSearch.isVisible = true
        binding.progressBarWebView.isVisible = true
        binding.textViewProgressBar.isVisible = true
        binding.textViewWebView404Text.isVisible = false
        binding.webView.isVisible = false

        search("")
        binding.editTextSearch.setOnClickListener {
            val string = binding.editTextSearch.text.toString()
            search(string)
        }

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

    private fun search (search: String){

        // View web content
        val url = ("$WEB_VIEW_URL_SEARCH?s=$search")
        context?.let { WebViewSearch(it).run(requireActivity(), binding, url) }
    }
}
