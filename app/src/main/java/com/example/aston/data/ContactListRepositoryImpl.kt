package com.example.aston.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aston.domain.Contact
import com.example.aston.domain.ContactListRepository
import io.github.serpro69.kfaker.Faker

object ContactListRepositoryImpl : ContactListRepository {

    private val contactListLD = MutableLiveData<List<Contact>>()
    private val contactList = sortedSetOf<Contact>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId: Int = 0
    
    init {
        val faker = Faker()
        
        for (i in 0 until 100) {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            val phoneNumber = faker.phoneNumber.phoneNumber()
            
            val contact = Contact(firstName, lastName, phoneNumber)
            addContact(contact)
        }
    }

    override fun addContact(contact: Contact) {
        if (contact.id == Contact.UNDEFINED_ID) {
            
            contact.id = autoIncrementId++
        }
        contactList.add(contact)
        updateList()
    }

    override fun deleteContact(contact: Contact) {
        contactList.remove(contact)
        updateList()
    }

    override fun editContact(contact: Contact) {
        val oldElement = getContact(contact.id)
        contactList.remove(oldElement)
        addContact(contact)
    }

    override fun getContact(contactId: Int): Contact {
        return contactList.find { it.id == contactId }
            ?: throw RuntimeException("Element with id $contactId not found")
    }

    override fun getContactList(): LiveData<List<Contact>> {
        return contactListLD
    }

    private fun updateList() {
        contactListLD.value = contactList.toList()
    }
}