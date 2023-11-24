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
    private lateinit var buttonChooseDelete: ImageButton
    private lateinit var buttonDelete: Button
    private lateinit var buttonCancel: Button

    private val listToDelete = arrayListOf<Contact>()

    private var isVisibleCheckBox = false

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
        buttonChooseDelete = view.findViewById(R.id.button_choose_delete)
        buttonDelete = view.findViewById(R.id.button_delete)
        buttonCancel = view.findViewById(R.id.button_cancel)

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
        setupSwipeListener(rvShopList)
    }

    // Метод для свайпа
    private fun setupSwipeListener(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // получить текущий список через метод currentList
                val item = contactListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteContact(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        contactListAdapter.onContactClickListener = {
            val fragment = ContactFragment.newInstanceEditContact(it.id)

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit()
        }

        contactListAdapter.onCheckBoxClickListener = {
            if (listToDelete.contains(it)) {
                listToDelete.remove(it)
            } else {
                listToDelete.add(it)
            }
        }

        buttonAdd.setOnClickListener {
            val fragment = ContactFragment.newInstanceAddContact()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit()
        }

        buttonChooseDelete.setOnClickListener {
            if (!isVisibleCheckBox) {
                buttonAdd.visibility = View.GONE
                buttonCancel.visibility = View.VISIBLE
                buttonDelete.visibility = View.VISIBLE

                isVisibleCheckBox = true
                viewModel.changeVisibilityState()
                listToDelete.clear()
            }
        }

        buttonCancel.setOnClickListener {
            if (isVisibleCheckBox) {
                enableDeletingView()

                isVisibleCheckBox = false
                viewModel.changeVisibilityState()
                listToDelete.clear()
            }
        }

        buttonDelete.setOnClickListener {
            if (isVisibleCheckBox) {

                for (contact in listToDelete) {
                    viewModel.deleteContact(contact)
                }
                listToDelete.clear()

                enableDeletingView()

                isVisibleCheckBox = false
                viewModel.changeVisibilityState()
                listToDelete.clear()
            }
        }
    }

    private fun enableDeletingView() {
        buttonAdd.visibility = View.VISIBLE
        buttonCancel.visibility = View.GONE
        buttonDelete.visibility = View.GONE
    }
}