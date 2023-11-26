package com.example.aston.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.aston.R
import com.example.aston.domain.Contact

class ContactFragment : Fragment() {

    private lateinit var viewModel: ContactViewModel

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var contactId: Int = Contact.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        initViews(view)
//        launchRightMode()
//        observeViewModel()

        launch()
    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(CONTACT_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            contactId = args.getInt(CONTACT_ID, Contact.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        editTextFirstName = view.findViewById(R.id.editTextFirstName)
        editTextLastName = view.findViewById(R.id.editTextLastName)
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        buttonSave = view.findViewById(R.id.buttonSave)
    }

    private fun launch() {
        setFragmentResultListener("contactData") { _, bundle ->
            val contactData = bundle.getStringArrayList("contactData")

            editTextFirstName.setText(contactData?.get(0) ?: "")
            editTextLastName.setText(contactData?.get(1) ?: "")
            editTextPhoneNumber.setText(contactData?.get(2) ?: "")
        }

        buttonSave.setOnClickListener {
            val firstName = editTextFirstName.text?.toString()
            val lastName = editTextLastName.text?.toString()
            val phoneNumber = editTextPhoneNumber.text?.toString()

            if (validateInput(firstName!!, lastName!!, phoneNumber!!)) {
                val contactArray = arrayListOf(
                    firstName,
                    lastName,
                    phoneNumber
                )

                parentFragmentManager.setFragmentResult(CONTACT_RESULT, Bundle().apply {
                    putStringArrayList(CONTACT_RESULT, contactArray)
                })

                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun validateInput(firstName: String, lastName: String, phoneNumber: String): Boolean {
        if (firstName.isBlank()) {
            return false
        }
        if (lastName.isBlank()) {
            return false
        }
        if (phoneNumber.isBlank()) {
            return false
        }
        return true
    }


//    private fun observeViewModel() {
//        viewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }

    companion object {
        const val SCREEN_MODE = "extra_mode"
        const val CONTACT_ID = "extra_contact_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        const val MODE_UNKNOWN = ""

        const val CONTACT_DATA = "contactData"
        const val CONTACT_RESULT = "contactResult"


        fun newInstanceAddContact(): ContactFragment {
            val args = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
            val fragment = ContactFragment().apply {
                arguments = args
            }
            return fragment
        }

        fun newInstanceEditContact(contactId: Int): ContactFragment {
            val args = Bundle().apply {
                putString(SCREEN_MODE, MODE_EDIT)
                putInt(CONTACT_ID, contactId)
            }
            val fragment = ContactFragment().apply {
                arguments = args
            }
            return fragment
        }
    }
}