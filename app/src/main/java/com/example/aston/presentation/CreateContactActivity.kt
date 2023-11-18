package com.example.aston.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.aston.R

class CreateContactActivity : AppCompatActivity() {
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonSave = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            saveContact()
        }
    }

    private fun saveContact() {
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val phoneNumber = editTextPhoneNumber.text.toString()

//        if (firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank()) {
//            val newContact = Contact(contacts.size + 1, firstName, lastName, phoneNumber)
//            contacts.add(newContact)
//
//            clearFields()
//        } else {
//            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun clearFields() {
        editTextFirstName.text.clear()
        editTextLastName.text.clear()
        editTextPhoneNumber.text.clear()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_CONTACT_ID = "extra_contact_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, CreateContactActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, contactId: Int): Intent {
            val intent = Intent(context, CreateContactActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_CONTACT_ID, contactId)
            return intent
        }
    }
}