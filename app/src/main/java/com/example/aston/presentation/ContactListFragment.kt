package com.example.aston.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.aston.R
import com.example.aston.domain.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactListFragment : Fragment() {

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
            val fragment = ContactFragment.newInstanceEditContact(it.id)

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit()
        }

        buttonAdd.setOnClickListener {
            val fragment = ContactFragment.newInstanceAddContact()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit()
        }

    }
}