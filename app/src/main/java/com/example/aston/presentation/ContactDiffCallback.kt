package com.example.aston.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.aston.domain.Contact

class ContactDiffCallback: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}