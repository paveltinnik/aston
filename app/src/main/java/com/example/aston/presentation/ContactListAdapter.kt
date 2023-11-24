package com.example.aston.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.aston.R
import com.example.aston.domain.Contact

class ContactListAdapter : ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback()) {

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 30
    }

    var onContactLongClickListener: ((Contact) -> Unit)? = null
    var onContactClickListener: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layout = R.layout.item_contact
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ContactViewHolder(view)
    }

    // Установить во viewHolder значения
    override fun onBindViewHolder(viewHolder: ContactViewHolder, position: Int) {
        // получить shopItem по позиции
        val contact = getItem(position)

        viewHolder.view.setOnLongClickListener {
            onContactLongClickListener?.invoke(contact)
            true
        }

        viewHolder.view.setOnClickListener {
            onContactClickListener?.invoke(contact)
        }

        viewHolder.tvFirstName.text = contact.firstName
        viewHolder.tvLastName.text = contact.lastName
        viewHolder.tvPhoneNumber.text = contact.phoneNumber
    }

    // Вернуть значение в зависимости от того, кокой item_view используется
//    override fun getItemViewType(position: Int): Int {
//        val item = getItem(position)
//        return 1
//    }
}