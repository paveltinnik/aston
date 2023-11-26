package com.example.aston.presentation

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.aston.R
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

class ContactFragment : Fragment() {

    private lateinit var viewModel: ContactViewModel

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonSave: Button
    private lateinit var ivContactPhoto: ImageView

    private var contactPhoto: Bitmap? = null

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
        launch()
    }

    private fun initViews(view: View) {
        ivContactPhoto = view.findViewById(R.id.iv_contact_photo)
        editTextFirstName = view.findViewById(R.id.editTextFirstName)
        editTextLastName = view.findViewById(R.id.editTextLastName)
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        buttonSave = view.findViewById(R.id.buttonSave)
    }

    private fun launch() {
        setFragmentResultListener(CONTACT_DATA) { _, bundle ->
            val contactData = bundle.getStringArrayList(CONTACT_DATA)
            val photo: Bitmap? = bundle.getParcelable(CONTACT_PHOTO)

            if (photo != null) {
                contactPhoto = photo
                ivContactPhoto.setImageBitmap(photo)
            }

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
                    putParcelable(CONTACT_PHOTO, contactPhoto)
                })

                parentFragmentManager.popBackStack()
            }
        }

        ivContactPhoto.setOnClickListener {
            loadPhoto {
                contactPhoto = it
                activity?.runOnUiThread {
                    ivContactPhoto.setImageBitmap(it)
                }
            }
        }
    }

    private fun loadPhoto(callback: (Bitmap) -> Unit) {
        thread {
            val photo: Bitmap = Picasso.get().load("https://loremflickr.com/640/360").get()
            callback.invoke(photo)
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

    companion object {
        const val CONTACT_DATA = "contactData"
        const val CONTACT_RESULT = "contactResult"
        const val CONTACT_PHOTO = "contactPhoto"
    }
}