package com.example.aston.presentation

import androidx.lifecycle.ViewModel
import com.example.aston.data.ContactListRepositoryImpl
import com.example.aston.domain.Contact
import com.example.aston.domain.DeleteContactUseCase
import com.example.aston.domain.EditContactUseCase
import com.example.aston.domain.GetContactListUseCase

class MainViewModel: ViewModel() {

    private val repository = ContactListRepositoryImpl

    private val getShopListUseCase = GetContactListUseCase(repository)
    private val deleteShopItemUseCase = DeleteContactUseCase(repository)
    private val editContactUseCase = EditContactUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(contact: Contact) {
        deleteShopItemUseCase.deleteContact(contact)
    }

//    fun changeEnableState(contact: Contact) {
//        val newItem = contact.copy(enabled = !contact.enabled)
//        editContactUseCase.editContact(newItem)
//    }
}