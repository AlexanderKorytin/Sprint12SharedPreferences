package com.example.sprint12sharedpreferences

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val PREFERENCES = "Preferences12"
const val EDIT_TEXT_KEY = "Edit_text"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editTextView = findViewById<EditText>(R.id.editText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val restoreButton = findViewById<Button>(R.id.restoreButton)
        val shared = getSharedPreferences(PREFERENCES, MODE_PRIVATE)
        var textSaved = ""
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    textSaved = editTextView.text.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }

        editTextView.addTextChangedListener(textWatcher)
        textSaved = shared.getString(EDIT_TEXT_KEY, "").toString()
        editTextView.setText(textSaved)
        saveButton.setOnClickListener {
            shared.edit()
                .putString(EDIT_TEXT_KEY, textSaved)
                .apply()
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(editTextView.windowToken, 0)
            Toast.makeText(this, "Save value ${textSaved}", Toast.LENGTH_SHORT)
                .show()
        }

        restoreButton.setOnClickListener {
            textSaved = shared.getString(EDIT_TEXT_KEY, "").toString()
            editTextView.setText(textSaved)
        }

        editTextView.setOnClickListener { editTextView.selectAll() }
    }
}