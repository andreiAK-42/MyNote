package com.example.mynote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.NoteEntity

class ToDoAdapter(
    private var noteList: MutableList<NoteEntity>,
    private val listener: OnNoteDeleteListener
) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_item_title)
        val markImageView: ImageView = itemView.findViewById(R.id.note_item_mark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.title.text = note.title

        if (note.state == 1) {
            holder.markImageView.setImageResource(R.drawable.check_mark_button)
        } else if (note.state == 2) {
            holder.markImageView.setImageResource(R.drawable.cross_mark_button)
        } else {
            holder.markImageView.setImageResource(R.drawable.white_medium_square)
        }

        holder.markImageView.setOnClickListener {
            if (note.state == 0) {
                holder.markImageView.setImageResource(R.drawable.check_mark_button)
                note.state = 1
            } else if (note.state == 1) {
                holder.markImageView.setImageResource(R.drawable.cross_mark_button)
                note.state = 2
            } else {
                holder.markImageView.setImageResource(R.drawable.white_medium_square)
                note.state = 0
            }
            listener.onNoteUpdate(note)
        }

        holder.itemView.setOnLongClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {

                deleteItem(position)
            }
            true
        }
    }

    fun deleteItem(position: Int) {
        listener.onNoteDelete(noteList[position])
        noteList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, noteList.size)
    }

    fun updateList(newList: List<NoteEntity>) {
        noteList = newList.toMutableList()
        notifyDataSetChanged()
    }

    interface OnNoteDeleteListener {
        fun onNoteDelete(note: NoteEntity)
        fun onNoteUpdate(note: NoteEntity)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}
