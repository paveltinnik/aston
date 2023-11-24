package com.example.aston.domain

import android.graphics.Bitmap

data class Contact(
    val photo: Bitmap? = null,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}