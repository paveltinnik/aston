package com.example.aston.presentation

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.aston.R
import com.example.aston.data.ContactListRepositoryImpl
import com.example.aston.domain.AddContactUseCase
import com.example.aston.domain.Contact
import com.example.aston.domain.EditContactUseCase
import com.example.aston.domain.GetContactUseCase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactListFragment : Fragment() {

    private val repository = ContactListRepositoryImpl

    private val addContactUseCase = AddContactUseCase(repository)
    private val editContactUseCase = EditContactUseCase(repository)

    private lateinit var viewModel: MainViewModel
    private lateinit var contactListAdapter: ContactListAdapter

    private lateinit var buttonAdd: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAdd = view.findViewById(R.id.button_add_contact)

        setupRecyclerView(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.contactList.observe(viewLifecycleOwner) {
            // обновить список из другого потока
            contactListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView(view: View) {
        val rvShopList = view.findViewById<RecyclerView>(R.id.rv_shop_list)

        with(rvShopList) {
            contactListAdapter = ContactListAdapter()
            adapter = contactListAdapter

            recycledViewPool.setMaxRecycledViews(
                ContactListAdapter.VIEW_TYPE_ENABLED,
                ContactListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ContactListAdapter.VIEW_TYPE_DISABLED,
                ContactListAdapter.MAX_POOL_SIZE
            )
        }

        setupClickListener()
    }

    private fun setupClickListener() {
        contactListAdapter.onContactClickListener = {

            parentFragmentManager.setFragmentResultListener(
                ContactFragment.CONTACT_RESULT,
                this
            ) { _, bundle ->
                val resultArray = bundle.getStringArrayList(ContactFragment.CONTACT_RESULT)

                val contactPhoto: Bitmap? = bundle.getParcelable(ContactFragment.CONTACT_PHOTO)
                val firstName = resultArray?.get(0) ?: ""
                val lastName = resultArray?.get(1) ?: ""
                val phone = resultArray?.get(2) ?: ""

                val contact = it.copy(photo = contactPhoto, firstName = firstName, lastName = lastName, phoneNumber = phone)
                editContactUseCase.editContact(contact)
                contactListAdapter.notifyDataSetChanged()
                childFragmentManager.clearFragmentResultListener(ContactFragment.CONTACT_RESULT)
            }

            setFragmentResult(ContactFragment.CONTACT_DATA, Bundle().apply {
                val contactData = arrayListOf(it.firstName, it.lastName, it.phoneNumber)
                putStringArrayList(ContactFragment.CONTACT_DATA, contactData)

                val contactPhoto = it.photo
                putParcelable(ContactFragment.CONTACT_PHOTO, contactPhoto)
            })

            openContactFragment()
        }

        buttonAdd.setOnClickListener {

            parentFragmentManager.setFragmentResultListener(
                ContactFragment.CONTACT_RESULT,
                this
            ) { _, bundle ->
                val resultArray = bundle.getStringArrayList(ContactFragment.CONTACT_RESULT)

                val firstName = resultArray?.get(0) ?: ""
                val lastName = resultArray?.get(1) ?: ""
                val phone = resultArray?.get(2) ?: ""

                val contact = Contact(firstName = firstName, lastName = lastName, phoneNumber = phone)
                addContactUseCase.addContact(contact)
                contactListAdapter.notifyDataSetChanged()
                childFragmentManager.clearFragmentResultListener(ContactFragment.CONTACT_RESULT)
            }

            openContactFragment()
        }
    }

    private fun openContactFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, ContactFragment())
            .addToBackStack(null)
            .commit()
    }
}