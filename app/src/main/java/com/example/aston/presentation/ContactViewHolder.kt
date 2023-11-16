package com.example.aston.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aston.R

class ContactViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvFirstName = view.findViewById<TextView>(R.id.tv_first_name)
    val tvLastName = view.findViewById<TextView>(R.id.tv_last_name)
    val tvPhoneNumber = view.findViewById<TextView>(R.id.tv_phone_number)
}