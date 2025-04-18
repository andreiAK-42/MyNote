import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mynote.R
import database.NoteEntity

class NoteAdapter(
    context: Context,
    notes: MutableList<NoteEntity>
) : ArrayAdapter<NoteEntity>(context, R.layout.note_item, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.note_item, parent, false)

        val note = getItem(position) ?: return view

        view.findViewById<TextView>(R.id.note_item_title).text = note.title
        //view.findViewById<TextView>(R.id.etview_description).text = note.description

        return view
    }

    fun updateData(newNotes: List<NoteEntity>) {
        clear()
        addAll(newNotes)
        notifyDataSetChanged()
    }
}