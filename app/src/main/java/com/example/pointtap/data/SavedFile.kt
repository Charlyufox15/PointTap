package com.example.pointtap.data

import android.net.Uri

data class SavedFile(
    val name: String,
    val uri: Uri,
    val date: Long
)
