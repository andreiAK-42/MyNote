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

        var addMode: Int = 1

        findViewById<Chip>(R.id.chip_back).setOnClickListener {
            finish()
        }


        if (intent.extras != null && intent.extras!!.getString("title") != null) {
            findViewById<EditText>(R.id.etview_title).setText(
                intent.extras!!.getString("title").toString()
            )
            findViewById<EditText>(R.id.etview_description).setText(
                intent.extras!!.getString("description").toString()
            )
            addMode = 0
        }

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

                if (addMode == 0) {
                    resultIntent.putExtra("id", intent.extras!!.getInt("id"))
                    resultIntent.putExtra("state", intent.extras!!.getInt("state"))
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

        }
    }
}