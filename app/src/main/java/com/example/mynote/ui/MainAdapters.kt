package com.example.mynote.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.R
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

        when (note.state) {
            0 -> {
                holder.markImageView.setImageResource(R.drawable.white_medium_square)
            }

            1 -> {
                holder.markImageView.setImageResource(R.drawable.check_mark_button)
                holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            2 -> {
                holder.markImageView.setImageResource(R.drawable.cross_mark_button)
            }
        }

        holder.markImageView.setOnClickListener {
            when (note.state) {
                0 -> {
                    holder.markImageView.setImageResource(R.drawable.check_mark_button)
                    holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    note.state = 1
                }

                1 -> {
                    holder.markImageView.setImageResource(R.drawable.cross_mark_button)
                    holder.title.paintFlags =
                        holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    note.state = 2
                }

                2 -> {
                    holder.markImageView.setImageResource(R.drawable.white_medium_square)
                    note.state = 0
                }
            }
            listener.onNoteUpdate(note)
        }

        holder.title.setOnClickListener {
            listener.onViewNote(note)
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
        fun onViewNote(note: NoteEntity)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}
