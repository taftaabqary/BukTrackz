package com.unsoed.core.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unsoed.core.databinding.ItemNoteBinding
import com.unsoed.core.domain.entity.Note

class NoteAdapter(private val onWriteNote: (Int, String) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var listNote: MutableList<Note> = mutableListOf()

    fun addNote(note: List<Note>) {
        listNote.addAll(note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val data = listNote[position]
        holder.bind(data)
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.tiNote.hint = "Note ${item.id}"
            binding.etNote.setText(item.note)
            binding.etNote.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().isNotEmpty()) {
                        onWriteNote(item.id, s.toString().trim())
                    }
                }

            })
        }
    }
}