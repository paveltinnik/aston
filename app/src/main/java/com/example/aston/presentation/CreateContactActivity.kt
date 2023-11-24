package com.example.aston.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aston.R
import com.example.aston.domain.Contact

class CreateContactActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactViewModel

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var contactId: Int = Contact.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        parseIntent()

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        initViews()
        launchRightMode()
        observeViewModel()
    }

    private fun initViews() {
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonSave = findViewById(R.id.buttonSave)
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getContact(contactId)

        viewModel.contact.observe(this) {
            editTextFirstName.setText(it.firstName)
            editTextLastName.setText(it.lastName)
            editTextPhoneNumber.setText(it.phoneNumber)
        }

        buttonSave.setOnClickListener {
            viewModel.editContact(
                editTextFirstName.text?.toString(),
                editTextLastName.text?.toString(),
                editTextPhoneNumber.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addContact(
                editTextFirstName.text?.toString(),
                editTextLastName.text?.toString(),
                editTextPhoneNumber.text?.toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = intent.getStringExtra(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(CONTACT_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            contactId = intent.getIntExtra(CONTACT_ID, Contact.UNDEFINED_ID)
        }
    }

    companion object {
        const val SCREEN_MODE = "extra_mode"
        const val CONTACT_ID = "extra_shop_item_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, CreateContactActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, contactId: Int): Intent {
            val intent = Intent(context, CreateContactActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(CONTACT_ID, contactId)
            return intent
        }
    }
}