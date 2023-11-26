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

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact
}