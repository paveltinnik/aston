package com.example.aston.domain

class EditContactUseCase(private val contactListRepository: ContactListRepository) {

    fun editContact(contact: Contact) {
        contactListRepository.editContact(contact)
    }
}