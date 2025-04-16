package com.example.mynote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        findViewById<Chip>(R.id.btn_save).setOnClickListener {
            val resultIntent = Intent()
            val title: String = findViewById<EditText>(R.id.etview_title).text.toString()
            val description: String =
                findViewById<EditText>(R.id.etview_description).text.toString()

            if (title == "Title" || title == "" || description == "") {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                resultIntent.putExtra("title", title)
                resultIntent.putExtra("description", description)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}