package com.example.aston.domain

import androidx.lifecycle.LiveData

class GetContactListUseCase(private val contactListRepository: ContactListRepository) {

    fun getShopList(): LiveData<List<Contact>> {
        return contactListRepository.getContactList()
    }
}