package de.linkelisteortenau.app.ui.contact

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2022-12-01
 *
 * Contact Fragment of the Organisation
 **/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import de.linkelisteortenau.app.backend.externalPackage.Facebook
import de.linkelisteortenau.app.backend.externalPackage.MailTo
import de.linkelisteortenau.app.backend.externalPackage.Signal
import de.linkelisteortenau.app.backend.externalPackage.Telegram
import de.linkelisteortenau.app.databinding.FragmentContactBinding

/**
 * Class for the contact content
 **/
class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
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
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonImgMail: ImageView = binding.imageMail
        val buttonTxtMail: TextView = binding.txtMailTitle
        val buttonImgSignal: ImageView = binding.imageSignal
        val buttonTxtSignal: TextView = binding.txtSignalGroup
        val buttonImgTelegram: ImageView = binding.imageTelegram
        val buttonTxtTelegram: TextView = binding.txtTelegramGroup
        val buttonImgFacebook: ImageView = binding.imageFacebook
        val buttonTxtFacebook: TextView = binding.txtFacebook

        // Mail to Organisation
        buttonImgMail.setOnClickListener {
            context?.let { MailTo(it).performEmail() }
        }

        buttonTxtMail.setOnClickListener {
            context?.let { MailTo(it).performEmail() }
        }

        // Signal group link
        buttonImgSignal.setOnClickListener {
            context?.let { Signal(it).performToGroupLink() }
        }

        buttonTxtSignal.setOnClickListener {
            context?.let { Signal(it).performToGroupLink() }
        }

        // Telegram group link
        buttonImgTelegram.setOnClickListener {
            context?.let { Telegram(it).performToGroupLink() }
        }

        buttonTxtTelegram.setOnClickListener {
            context?.let { Telegram(it).performToGroupLink() }
        }

        // Facebook follow
        buttonImgFacebook.setOnClickListener {
            context?.let { Facebook(it).performToGroup() }
        }

        buttonTxtFacebook.setOnClickListener {
            context?.let { Facebook(it).performToGroup() }
        }
        return root
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