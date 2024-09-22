package me.wibisa.coconote.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.wibisa.coconote.R
import me.wibisa.coconote.adapter.NoteAdapter
import me.wibisa.coconote.adapter.NoteListener
import me.wibisa.coconote.core.domain.model.Note
import me.wibisa.coconote.core.util.Constant
import me.wibisa.coconote.core.util.gone
import me.wibisa.coconote.core.util.show
import me.wibisa.coconote.databinding.FragmentHomeBinding
import me.wibisa.coconote.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteAdapter
    private val viewModel: HomeViewModel by viewModels()
    private val mainNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {

        binding.fabCreateNewNote.setOnClickListener {
            mainNavController?.navigate(R.id.action_homeFragment_to_writeNoteFragment)
        }

        adapter = NoteAdapter(
            NoteListener(
                clickListener = { note ->
                    navigateToWriteNoteFragment(note)
                },
                longClickListener = { note ->
                    makeDeleteNoteDialogAlert(note)
                }
            )
        )
        binding.rvNotes.adapter = adapter

        observeLisOfNoteUiState()
    }

    private fun observeLisOfNoteUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listOfNoteUiState.collect { noteList ->
                    if (noteList.isEmpty()) binding.messageIsEmpty.show() else binding.messageIsEmpty.gone()
                    adapter.submitList(noteList)
                }
            }
        }
    }

    private fun navigateToWriteNoteFragment(note: Note) {
        val bundle = bundleOf(
            Constant.ID to note.id,
            Constant.TITLE to note.title,
            Constant.DESCRIPTION to note.description,
        )

        mainNavController?.navigate(
            R.id.action_homeFragment_to_writeNoteFragment,
            bundle
        )
    }

    private fun makeDeleteNoteDialogAlert(note: Note) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_confirm))
            .setMessage(getString(R.string.delete_confirm_description))
            .setPositiveButton(getString(R.string.delete)) { dialog, _ ->
                viewModel.deleteNote(note.id)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}