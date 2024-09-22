package me.wibisa.coconote.ui.writenote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import me.wibisa.coconote.R
import me.wibisa.coconote.core.domain.model.Note
import me.wibisa.coconote.core.util.Constant
import me.wibisa.coconote.core.util.Util
import me.wibisa.coconote.databinding.FragmentWriteNoteBinding
import me.wibisa.coconote.viewmodel.WriteNoteViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class WriteNoteFragment : Fragment() {

    private lateinit var binding: FragmentWriteNoteBinding
    private val viewModel: WriteNoteViewModel by viewModels()
    private val mainNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleOnBackOrClosePressed()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWriteNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {
        binding.ibClose.setOnClickListener {
            handleOnBackOrClosePressed()
        }

        binding.ibSave.setOnClickListener {
            handleOnSavePressed()
        }

        bindNote()
    }

    private fun handleOnSavePressed() {
        val title = binding.edTitle.text.toString()
        val description = binding.edDescription.text.toString()
        val id = arguments?.getString(Constant.ID)

        if (title.isNotEmpty() || description.isNotEmpty()) {
            if (id != null) {
                updateNote()
            } else {
                createNote()
            }
        } else {
            makeDialogAlert()
        }
    }

    private fun handleOnBackOrClosePressed() {
        val isNewNote = arguments?.getBoolean(Constant.NEW_NOTE)
        val titleOld = arguments?.getString(Constant.TITLE)
        val descriptionOld = arguments?.getString(Constant.DESCRIPTION)
        val titleNew = binding.edTitle.text.toString()
        val descriptionNew = binding.edDescription.text.toString()
        val id = arguments?.getString(Constant.ID)

        if (isNewNote == true || (titleOld.equals(titleNew) && descriptionOld.equals(descriptionNew))) {
            mainNavController?.popBackStack()
        } else {
            if (titleNew.isNotEmpty() || descriptionNew.isNotEmpty()) {
                if (id != null) {
                    updateNote()
                } else {
                    createNote()
                }
                mainNavController?.popBackStack()
            }
        }
    }

    private fun bindNote() {
        val id = arguments?.getString(Constant.ID)
        val title = arguments?.getString(Constant.TITLE)
        val description = arguments?.getString(Constant.DESCRIPTION)

        if (id != null) {
            binding.edTitle.setText(title)
            binding.edDescription.setText(description)
        }
    }

    private fun createNote() {
        val title = binding.edTitle.text.toString()
        val description = binding.edDescription.text.toString()
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = currentDate.format(formatter)
        val timestamp = System.currentTimeMillis().toString()

        val note = Note(
            id = Util.generateUniqueId(),
            title = title,
            description = description,
            date = formattedDate,
            timestamp = timestamp
        )

        viewModel.createNote(note)
    }

    private fun updateNote() {
        val id = arguments?.getString(Constant.ID)
        val title = binding.edTitle.text.toString()
        val description = binding.edDescription.text.toString()
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = currentDate.format(formatter)
        val timestamp = System.currentTimeMillis().toString()

        val note =
            Note(
                id = id!!,
                title = title,
                description = description,
                date = formattedDate,
                timestamp = timestamp
            )

        viewModel.updateNote(note)
    }

    private fun makeDialogAlert() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.save_failed))
            .setMessage(getString(R.string.save_failed_description))
            .setPositiveButton(getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}