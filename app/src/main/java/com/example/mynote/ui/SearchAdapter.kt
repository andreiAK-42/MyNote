import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mynote.EditActivity
import com.example.mynote.R
import com.example.mynote.ui.ToDoAdapter
import database.NoteEntity

class SearchAdapter(
    context: Context,
    notes: MutableList<NoteEntity>,
    private val listener: SearchAdapter.OnSearchAdapterListener
) : ArrayAdapter<NoteEntity>(context, R.layout.note_item, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.note_item, parent, false)

        val note = getItem(position) ?: return view
        val markImageView = view.findViewById<ImageView>(R.id.note_item_mark)
        val title = view.findViewById<TextView>(R.id.note_item_title)

        title.text = note.title

        when (note.state) {
            0 -> {
                markImageView.setImageResource(R.drawable.white_medium_square)
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            1 -> {
                markImageView.setImageResource(R.drawable.check_mark_button)
                title.paintFlags =
                    title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            2 -> {
                markImageView.setImageResource(R.drawable.cross_mark_button)
            }
        }

        view.setOnClickListener {
            listener.OnSelectItem(note)
        }

        return view
    }

    interface OnSearchAdapterListener {
        fun OnSelectItem(note: NoteEntity)
    }

    fun updateData(newNotes: List<NoteEntity>) {
        clear()
        addAll(newNotes)
        notifyDataSetChanged()
    }
}