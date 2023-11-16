package com.example.aston

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.aston.domain.Contact

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
}