package com.example.aston.domain

import androidx.lifecycle.LiveData

interface ContactListRepository {
    fun addContact(contact: Contact)

    fun deleteContact(contact: Contact)

    fun editContact(contact: Contact)

    fun getContact(contactId: Int) : Contact

    fun getContactList():LiveData<List<Contact>>
}