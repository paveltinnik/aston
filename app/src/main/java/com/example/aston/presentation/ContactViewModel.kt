package com.example.aston.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aston.data.ContactListRepositoryImpl
import com.example.aston.domain.AddContactUseCase
import com.example.aston.domain.Contact
import com.example.aston.domain.EditContactUseCase
import com.example.aston.domain.GetContactUseCase

class ContactViewModel : ViewModel() {

    private val repository = ContactListRepositoryImpl

    private val getContactUseCase = GetContactUseCase(repository)
    private val addContactUseCase = AddContactUseCase(repository)
    private val editContactUseCase = EditContactUseCase(repository)

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getContact(contactId: Int) {
        val item = getContactUseCase.getContact(contactId)
        _contact.value = item
    }

    fun addContact(inputFirstName: String?,inputLastName: String?, inputPhoneNumber: String?) {
        val firstName = parseString(inputFirstName)
        val lastName = parseString(inputLastName)
        val phoneNumber = parseString(inputPhoneNumber)

        val fieldsValid = validateInput(firstName, lastName, phoneNumber)

        if (fieldsValid) {
            val contact = Contact(firstName, lastName, phoneNumber)
            addContactUseCase.addContact(contact)
            finishWork()
        }
    }

    fun editContact(inputFirstName: String?, inputLastName: String?, inputPhoneNumber: String?) {
        val firstName = parseString(inputFirstName)
        val lastName = parseString(inputLastName)
        val phoneNumber = parseString(inputPhoneNumber)

        val fieldsValid = validateInput(firstName, lastName, phoneNumber)

        if (fieldsValid) {
            _contact.value?.let {
                val contact = it.copy(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber)
                editContactUseCase.editContact(contact)
            }
            finishWork()
        }
    }

    private fun parseString(string: String?): String {
        return string?.trim() ?: ""
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

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}