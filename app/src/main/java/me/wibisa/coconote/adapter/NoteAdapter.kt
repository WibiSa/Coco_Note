package me.wibisa.coconote.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.wibisa.coconote.R
import me.wibisa.coconote.core.domain.model.Note
import me.wibisa.coconote.databinding.ItemNoteBinding

class NoteAdapter(private val clickListener: NoteListener) :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(COMPARATOR) {

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note, clickListener: NoteListener) {
            val context = binding.root.context
            binding.apply {
                tvTitle.text = note.title
                tvDescription.text = note.description
                tvDate.text = note.date

                val colorArray = context.resources.getIntArray(R.array.random_colors)
                val randomColor = colorArray.random()
                cvNote.setCardBackgroundColor(ColorStateList.valueOf(randomColor))

                itemView.setOnClickListener {
                    clickListener.onClick(note)
                }

                itemView.setOnLongClickListener {
                    clickListener.onLongClick(note)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemNoteBinding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemNoteBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        if (note != null) holder.bind(note, clickListener)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem == newItem

        }
    }
}

class NoteListener(
    val clickListener: (note: Note) -> Unit,
    val longClickListener: (note: Note) -> Unit
) {
    fun onClick(note: Note) = clickListener(note)
    fun onLongClick(note: Note) = longClickListener(note)
}