package com.example.aston.domain

class DeleteContactUseCase(private val shopListRepository: ContactListRepository) {
    fun deleteContact(contact: Contact) {
        shopListRepository.deleteContact(contact)
    }
}