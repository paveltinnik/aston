package com.example.aston.presentation

import com.example.aston.domain.Contact

data class ContactItem (
    val contact: Contact,
    val selected: Boolean,
    val visibleCheckBox: Boolean
)