package com.example.aston.domain

data class Contact(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}