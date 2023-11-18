package com.example.aston.domain

import com.example.aston.domain.ContactListRepository

class GetContactUseCase(private val contactListRepository: ContactListRepository) {
    fun getContact(contactId: Int) : Contact {
        return contactListRepository.getContact(contactId)
    }
}