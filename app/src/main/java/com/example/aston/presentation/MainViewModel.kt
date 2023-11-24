package com.example.aston.presentation

import androidx.lifecycle.ViewModel
import com.example.aston.data.ContactListRepositoryImpl
import com.example.aston.domain.Contact
import com.example.aston.domain.DeleteContactUseCase
import com.example.aston.domain.EditContactUseCase
import com.example.aston.domain.GetContactListUseCase

class MainViewModel: ViewModel() {

    private val repository = ContactListRepositoryImpl

    private val getContactListUseCase = GetContactListUseCase(repository)
    private val deleteContactUseCase = DeleteContactUseCase(repository)
    private val editContactUseCase = EditContactUseCase(repository)

    val contactList = getContactListUseCase.getContactList()

    fun deleteContact(contact: Contact) {
        deleteContactUseCase.deleteContact(contact)
    }

    fun changeVisibilityState() {
        for (contact in contactList.value!!) {
            val newItem = contact.copy(visible = !contact.visible)
            editContactUseCase.editContact(newItem)
        }
    }
}