package com.example.aston.domain

class AddContactUseCase(private val contactListRepository: ContactListRepository) {
    fun addContact(contact: Contact) {
        contactListRepository.addContact(contact)
    }
}