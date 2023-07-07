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
import de.linkelisteortenau.app.backend.externalPackage.Instagram
import de.linkelisteortenau.app.backend.externalPackage.MailTo
import de.linkelisteortenau.app.backend.externalPackage.Signal
import de.linkelisteortenau.app.backend.externalPackage.Telegram
import de.linkelisteortenau.app.backend.externalPackage.Youtube
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
        val buttonImgInstagram: ImageView = binding.imageInstagram
        val buttonTxtInstagram: TextView = binding.txtInstagram
        val buttonImgYoutube: ImageView = binding.imageYoutube
        val buttonTxtYoutube: TextView = binding.txtYoutube

        // Mail to Organisation
        buttonImgMail.setOnClickListener {
            context?.let { MailTo(it).performEmail() }
        }

        buttonTxtMail.setOnClickListener {
            context?.let { MailTo(it).performEmail() }
        }

        // Signal Profile
        buttonImgSignal.setOnClickListener {
            context?.let { Signal(it).openSignalProfile() }
        }

        buttonTxtSignal.setOnClickListener {
            context?.let { Signal(it).openSignalProfile() }
        }

        // Telegram Profile
        buttonImgTelegram.setOnClickListener {
            context?.let { Telegram(it).openTelegramProfile() }
        }

        buttonTxtTelegram.setOnClickListener {
            context?.let { Telegram(it).openTelegramProfile() }
        }

        // Facebook Profile
        buttonImgFacebook.setOnClickListener {
            context?.let { Facebook(it).openFacebookProfile() }
        }

        buttonTxtFacebook.setOnClickListener {
            context?.let { Facebook(it).openFacebookProfile() }
        }

        // Instagram Profile
        buttonImgInstagram.setOnClickListener {
            context?.let { Instagram(it).openInstagramProfile() }
        }

        buttonTxtInstagram.setOnClickListener {
            context?.let { Instagram(it).openInstagramProfile() }
        }

        // YouTube Profile
        buttonImgYoutube.setOnClickListener {
            context?.let { Youtube(it).openYouTubeProfile() }
        }

        buttonTxtYoutube.setOnClickListener {
            context?.let { Youtube(it).openYouTubeProfile() }
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
}